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
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.service.CargoLoaderService;

import java.util.List;

@Slf4j
@Component
public class MinimumEmptySpaceAlgorithm implements LoadingCargoAlgorithm {

    private final CargoSorter cargoSorter;
    private final TransportSorter transportSorter;
    private final TransportCrudRepository transportDataRepository;
    private final List<Cargo> cargoList;
    private final CargoLoaderService cargoLoaderService;

    @Autowired
    public MinimumEmptySpaceAlgorithm(CargoSorter cargoSorter,
                                      @Qualifier("transportSorterByWeightDesc") TransportSorter transportSorter,
                                      TransportCrudRepository transportDataRepository,
                                      List<Cargo> cargoList,
                                      CargoLoaderService cargoLoaderService) {
        this.cargoSorter = cargoSorter;
        this.transportSorter = transportSorter;
        this.transportDataRepository = transportDataRepository;
        this.cargoList = cargoList;
        this.cargoLoaderService = cargoLoaderService;
    }

    /**
     * Выполняет алгоритм плотной погрузки грузов.
     * Этот метод сортирует список грузов по весу и список транспортных средств по весу уже загруженного груза,
     * а затем пытается загрузить каждый груз в транспортное средство.
     *
     * @see CargoSorter#sort(List)
     * @see TransportSorter#sort(TransportCrudRepository)
     * @see #loadCargo(Cargo, List)
     */

    @Override
    public void execute() {
        log.info("Старт алгоритма плотной погрузки");
        List<Transport> transports = transportSorter.sort(transportDataRepository);
        for (Cargo cargo : cargoSorter.sort(cargoList)) {
            log.info("Погрузка груза: {}", cargo);
            try {
                loadCargo(cargo, transports);
            } catch (NoPlaceException e) {
                log.warn(e.getMessage());
            }
        }
        log.info("Плотная погрузка окончена");
    }

    private void loadCargo(Cargo cargo, List<Transport> transports) {
        for (Transport transport : transports) {
            try {
                cargoLoaderService.load(cargo, transport);
                transportDataRepository.addCargoInTransport(transport, cargo);
                log.info("Груз успешно загружен: {}", cargo);
                return;
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        }
        throw new NoPlaceException("Нет транспорта для загрузки данного груза: " + cargo);
    }
}