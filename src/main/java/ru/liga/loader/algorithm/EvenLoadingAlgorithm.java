package ru.liga.loader.algorithm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.algorithm.util.CargoLoader;
import ru.liga.loader.algorithm.util.CargoSorter;
import ru.liga.loader.algorithm.util.TransportSorter;
import ru.liga.loader.db.CargoDataManager;
import ru.liga.loader.db.TransportDataManager;
import ru.liga.loader.exception.InvalidCargoSize;
import ru.liga.loader.exception.NoPlaceException;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class EvenLoadingAlgorithm implements LoadingCargoAlgorithm {

    private final CargoSorter cargoSorter;
    private final TransportSorter transportSorter;
    private final TransportDataManager transportDataManager;
    private final CargoDataManager cargoDataManager;
    private final CargoLoader cargoLoader;

    /**
     * Выполняет алгоритм равномерной погрузки грузов.
     * Этот метод сортирует список грузов по весу, а затем пытается загрузить каждый груз
     * в транспортное средство. Если загрузка груза не удается из-за его размера,
     * то соответствующее предупреждение регистрируется в журнале.
     *
     * @see CargoSorter#sort(List)
     * @see #loadCargo(Cargo)
     */

    @Override
    public void execute() {
        log.info("Старт алгоритма равномерной погрузки");
        List<Cargo> cargos = cargoSorter.sort(
                cargoDataManager.getData()
                        .values().stream()
                        .flatMap(List::stream)
                        .toList()
        );
        for (Cargo cargo : cargos) {
            log.info("Погрузка груза: {}", cargo);
            try {
                loadCargo(cargo);
                log.debug("Груз успешно погружен: {}", cargo);
            } catch (InvalidCargoSize e) {
                log.warn(e.getMessage());
            }
        }
        log.info("Равномерная погрузка окончена");
    }

    private void loadCargo(Cargo cargo) throws NoPlaceException {
        Optional<Transport> optional = findMostFreeTransport();
        optional.ifPresentOrElse(
                transport -> {
                    cargoLoader.load(cargo, transport);
                    transportDataManager.addCargoInTransport(transport, cargo);
                },
                () -> {
                    throw new NoPlaceException("Нет грузовика для погрузки груза: " + cargo);
                }
        );
    }

    private Optional<Transport> findMostFreeTransport() {
        log.debug("Поиск максимально незагруженного транспорта");
        return getFirstTransport(transportSorter.sort(transportDataManager));
    }

    private Optional<Transport> getFirstTransport(List<Transport> transports) {
        if (transports.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(transports.get(0));
    }
}
