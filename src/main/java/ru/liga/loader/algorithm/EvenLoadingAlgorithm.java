package ru.liga.loader.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.liga.loader.algorithm.util.CargoSorter;
import ru.liga.loader.algorithm.util.TransportSorter;
import ru.liga.loader.exception.InvalidCargoSize;
import ru.liga.loader.exception.NoPlaceException;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.util.CargoLoader;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class EvenLoadingAlgorithm implements LoadingCargoAlgorithm {

    private final CargoSorter cargoSorter;
    private final TransportSorter transportSorter;
    private final TransportCrudRepository transportDataRepository;
    private final List<Transport> transports;
    private final List<Cargo> cargos;
    private final CargoLoader cargoLoader;

    @Autowired
    public EvenLoadingAlgorithm(CargoSorter cargoSorter,
                                @Qualifier("transportSorterByOccupiedAreaAsc") TransportSorter transportSorter,
                                TransportCrudRepository transportDataRepository, List<Transport> transports,
                                List<Cargo> cargos,
                                CargoLoader cargoLoader) {
        this.cargoSorter = cargoSorter;
        this.transportSorter = transportSorter;
        this.transportDataRepository = transportDataRepository;
        this.transports = transports;
        this.cargos = cargos;
        this.cargoLoader = cargoLoader;
    }

    /**
     * Выполняет алгоритм равномерной погрузки грузов.
     * Данный алгоритм находит транспорт с наименьшим процентом заполненности,
     * чтобы в конечном результате грузовики были равномерно загружены относительно своего размера
     *
     * @see CargoSorter#sort(List)
     * @see #loadCargo(Cargo)
     */

    @Override
    public void execute() {
        log.info("Старт алгоритма равномерной погрузки");
        for (Cargo cargo : cargoSorter.sort(cargos)) {
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
        Optional<Transport> optional = findMostFreeTransport(cargo);
        optional.ifPresentOrElse(
                transport -> {
                    cargoLoader.load(cargo, transport);
                    transportDataRepository.addCargoInTransport(transport, cargo);
                },
                () -> {
                    throw new NoPlaceException("Нет грузовика для погрузки груза: " + cargo);
                }
        );
    }

    private Optional<Transport> findMostFreeTransport(Cargo cargo) {
        log.debug("Поиск максимально незагруженного транспорта");
        return getFirstCanToLoadTransport(
                transportSorter.sort(transportDataRepository, transports),
                cargo
        );
    }

    private Optional<Transport> getFirstCanToLoadTransport(List<Transport> transports, Cargo cargo) {
        for (Transport transport : transports) {
            if (transport.canBeLoaded(cargo)) {
                return Optional.of(transport);
            }
        }
        return Optional.empty();
    }
}
