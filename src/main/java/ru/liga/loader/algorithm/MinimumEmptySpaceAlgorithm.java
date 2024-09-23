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

@Slf4j
@RequiredArgsConstructor
public class MinimumEmptySpaceAlgorithm implements LoadingCargoAlgorithm {

    private final CargoSorter cargoSorter;
    private final TransportSorter transportSorter;
    private final TransportDataManager transportDataManager;
    private final CargoDataManager cargoDataManager;
    private final CargoLoader cargoLoader;

    @Override
    public void execute() {
        log.info("Старт алгоритма плотной погрузки");
        List<Cargo> cargos = cargoSorter.sort(cargoDataManager.getData());
        List<Transport> transports = transportSorter.sort(transportDataManager);
        for (Cargo cargo : cargos) {
            log.info("Погрузка груза: {}", cargo);
            loadCargo(cargo, transports);
        }
        log.info("Плотная погрузка окончена");
    }

    private void loadCargo(Cargo cargo, List<Transport> transports) {
        for (Transport transport : transports) {
            try {
                cargoLoader.load(cargo, transport);
                transportDataManager.addCargoInTransport(transport, cargo);
                log.debug("Груз успешно загружен: {}", cargo);
                return;
            } catch (NoPlaceException e) {
                log.debug(e.getMessage());
            } catch (InvalidCargoSize i) {
                log.debug(i.getMessage());
                return;
            }
        }
        throw new NoPlaceException("Нет транспорта для загрузки данного груза: " + cargo);
    }
}