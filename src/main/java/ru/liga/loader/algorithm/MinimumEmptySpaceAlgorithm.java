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
import ru.liga.loader.repository.CargoDataRepository;
import ru.liga.loader.repository.TransportDataRepository;
import ru.liga.loader.service.CargoLoaderService;

import java.util.List;

@Slf4j
@Component
public class MinimumEmptySpaceAlgorithm implements LoadingCargoAlgorithm {

    private final CargoSorter cargoSorter;
    private final TransportSorter transportSorter;
    private final TransportDataRepository transportDataRepository;
    private final CargoDataRepository cargoDataRepository;
    private final CargoLoaderService cargoLoaderService;

    @Autowired
    public MinimumEmptySpaceAlgorithm(CargoSorter cargoSorter,
                                      @Qualifier("transportSorterByWeightDesc") TransportSorter transportSorter,
                                      TransportDataRepository transportDataRepository,
                                      CargoDataRepository cargoDataRepository,
                                      CargoLoaderService cargoLoaderService) {
        this.cargoSorter = cargoSorter;
        this.transportSorter = transportSorter;
        this.transportDataRepository = transportDataRepository;
        this.cargoDataRepository = cargoDataRepository;
        this.cargoLoaderService = cargoLoaderService;
    }

    /**
     * Выполняет алгоритм плотной погрузки грузов.
     * Этот метод сортирует список грузов по весу и список транспортных средств по весу уже загруженного груза,
     * а затем пытается загрузить каждый груз в транспортное средство.
     *
     * @see CargoSorter#sort(List)
     * @see TransportSorter#sort(TransportDataRepository)
     * @see #loadCargo(Cargo, List)
     */

    @Override
    public void execute() {
        log.info("Старт алгоритма плотной погрузки");
        List<Cargo> cargos = cargoSorter.sort(
                cargoDataRepository.getData().values().stream().toList()
        );
        List<Transport> transports = transportSorter.sort(transportDataRepository);
        for (Cargo cargo : cargos) {
            log.info("Погрузка груза: {}", cargo);
            loadCargo(cargo, transports);
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
            } catch (NoPlaceException e) {
                log.debug(e.getMessage());
            } catch (InvalidCargoSize i) {
                log.warn(i.getMessage());
                return;
            }
        }
        throw new NoPlaceException("Нет транспорта для загрузки данного груза: " + cargo);
    }
}