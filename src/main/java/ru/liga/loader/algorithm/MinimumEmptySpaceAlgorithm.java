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
public class MinimumEmptySpaceAlgorithm implements LoadingCargoAlgorithm {

    private final CargoSorter cargoSorter;
    private final TransportSorter transportSorter;
    private final TransportCrudRepository transportDataRepository;
    private final List<Transport> transports;
    private final List<Cargo> cargoList;
    private final CargoLoader cargoLoader;
    private final CargoCrudRepository cargoCrudRepository;

    @Autowired
    public MinimumEmptySpaceAlgorithm(CargoSorter cargoSorter,
                                      @Qualifier("transportSorterByOccupiedAreaDesc") TransportSorter transportSorter,
                                      @Qualifier("transportCrudRepository") TransportCrudRepository transportDataRepository, List<Transport> transports,
                                      List<Cargo> cargoList,
                                      CargoLoader cargoLoader, @Qualifier("cargoCrudRepository") CargoCrudRepository cargoCrudRepository) {
        this.cargoSorter = cargoSorter;
        this.transportSorter = transportSorter;
        this.transportDataRepository = transportDataRepository;
        this.transports = transports;
        this.cargoList = cargoList;
        this.cargoLoader = cargoLoader;
        this.cargoCrudRepository = cargoCrudRepository;
    }

    /**
     * Выполняет алгоритм плотной погрузки грузов.
     * Метод находит наиболее заполненную машину для погрузки, если погрузка не удалась,
     * то алгоритм повторяется для следующего транспорта
     *
     * @see CargoSorter#sort(List)
     * @see TransportSorter#sort(TransportCrudRepository, List)
     * @see #loadCargo(Cargo, List)
     */

    @Override
    public void execute() {
        log.info("Старт алгоритма плотной погрузки");
        List<Transport> transportsSorted = transportSorter.sort(transportDataRepository, transports);
        for (Cargo cargo : cargoSorter.sort(cargoList)) {
            log.info("Погрузка груза: {}", cargo);
            try {
                loadCargo(cargo, transportsSorted);
            } catch (NoPlaceException e) {
                log.warn(e.getMessage());
            }
        }
        log.info("Плотная погрузка окончена");
    }

    private void loadCargo(Cargo cargo, List<Transport> transports) {
        for (Transport transport : transports) {
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