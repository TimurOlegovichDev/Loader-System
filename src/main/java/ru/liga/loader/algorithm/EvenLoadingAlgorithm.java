package ru.liga.loader.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.liga.loader.algorithm.util.CargoSorter;
import ru.liga.loader.algorithm.util.TransportSorter;
import ru.liga.loader.exception.NoPlaceException;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.CargoCrudRepository;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.util.CargoLoader;

import java.util.List;

@Slf4j
@Component
public class EvenLoadingAlgorithm implements LoadingCargoAlgorithm {

    private final CargoSorter cargoSorter;
    private final TransportSorter transportSorter;
    private final TransportCrudRepository transportDataRepository;
    private final List<Transport> transports;
    private final List<Cargo> cargos;
    private final CargoLoader cargoLoader;
    private final CargoCrudRepository cargoCrudRepository;

    @Autowired
    public EvenLoadingAlgorithm(CargoSorter cargoSorter,
                                @Qualifier("transportSorterByOccupiedAreaAsc") TransportSorter transportSorter,
                                @Qualifier("transportCrudRepository") TransportCrudRepository transportDataRepository,
                                List<Transport> transports,
                                List<Cargo> cargos,
                                CargoLoader cargoLoader,
                                @Qualifier("cargoCrudRepository") CargoCrudRepository cargoCrudRepository) {
        this.cargoSorter = cargoSorter;
        this.transportSorter = transportSorter;
        this.transportDataRepository = transportDataRepository;
        this.transports = transports;
        this.cargos = cargos;
        this.cargoLoader = cargoLoader;
        this.cargoCrudRepository = cargoCrudRepository;
    }

    /**
     * Выполняет алгоритм равномерной погрузки грузов.
     * Данный алгоритм находит транспорт с наименьшим процентом заполненности,
     * чтобы в конечном результате грузовики были равномерно загружены относительно своего размера
     *
     * @see CargoSorter#sort(List)
     */

    @Override
    public void execute() {
        log.info("Старт алгоритма равномерной погрузки");
        for (Cargo cargo : cargoSorter.sort(cargos)) {
            log.info("Погрузка груза: {}", cargo);
            try {
                loadCargo(cargo, transportSorter.sort(transportDataRepository, transports));
                log.debug("Груз успешно погружен: {}", cargo);
            } catch (NoPlaceException e) {
                log.warn(e.getMessage());
            }
        }
        log.info("Равномерная погрузка окончена");
    }

    private void loadCargo(Cargo cargo, List<Transport> sortedList) throws NoPlaceException {
        for (Transport transport : sortedList) {
            try {
                cargoLoader.load(cargo, transport);
                Cargo copy = new Cargo(cargo.getName(), cargo.getForm());
                copy.setTransportId(transport.getId());
                cargoCrudRepository.save(copy);
                transportDataRepository.save(transport);
                log.info("Груз успешно загружен: {}", cargo);
                return;
            } catch (NoPlaceException e) {
                log.debug(e.getMessage());
            }
        }
        throw new NoPlaceException("Нет транспорта для загрузки данного груза: " + cargo);
    }
}
