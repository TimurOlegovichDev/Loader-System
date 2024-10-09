package ru.liga.loadersystem.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.liga.loadersystem.algorithm.util.CargoSorter;
import ru.liga.loadersystem.algorithm.util.TransportSorter;
import ru.liga.loadersystem.exception.NoPlaceException;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.repository.CargoCrudRepository;
import ru.liga.loadersystem.repository.TransportCrudRepository;
import ru.liga.loadersystem.util.CargoLoader;

import java.util.List;

@Slf4j
@Component
public class MinimumEmptySpaceAlgorithm implements LoadingCargoAlgorithm {

    private final CargoSorter cargoSorter;
    private final TransportSorter transportSorter;
    private final TransportCrudRepository transportDataRepository;
    private final CargoLoader cargoLoader;
    private final CargoCrudRepository cargoCrudRepository;

    @Autowired
    public MinimumEmptySpaceAlgorithm(CargoSorter cargoSorter,
                                      @Qualifier("transportSorterByOccupiedAreaDesc") TransportSorter transportSorter,
                                      @Qualifier("transportCrudRepository") TransportCrudRepository transportDataRepository,
                                      CargoLoader cargoLoader,
                                      @Qualifier("cargoCrudRepository") CargoCrudRepository cargoCrudRepository) {
        this.cargoSorter = cargoSorter;
        this.transportSorter = transportSorter;
        this.transportDataRepository = transportDataRepository;
        this.cargoLoader = cargoLoader;
        this.cargoCrudRepository = cargoCrudRepository;
    }

    @Override
    public void execute(List<Cargo> cargos, List<Transport> transports) {
        log.info("Старт алгоритма плотной погрузки, количество грузовиков: {}", transports.size());
        List<Transport> transportsSorted = transportSorter.sort(transportDataRepository, transports);
        for (Cargo cargo : cargoSorter.sort(cargos)) {
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