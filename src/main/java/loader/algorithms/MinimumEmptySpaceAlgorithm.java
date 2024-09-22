package loader.algorithms;

import loader.algorithms.utils.CargoLoader;
import loader.algorithms.utils.CargoSorter;
import loader.algorithms.utils.TransportSorter;
import loader.algorithms.utils.TransportValidator;
import loader.db.CargoDataManager;
import loader.db.TransportDataManager;
import loader.exceptions.InvalidCargoSize;
import loader.exceptions.NoPlaceException;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MinimumEmptySpaceAlgorithm extends LoadingCargoAlgorithm {

    private final TransportSorter transportSorter;
    private final CargoSorter cargoSorter;

    public MinimumEmptySpaceAlgorithm(CargoLoader cargoLoader,
                                      CargoSorter cargoSorter,
                                      TransportSorter transportSorter,
                                      TransportValidator transportValidator) {
        super(cargoLoader, transportValidator);
        this.cargoSorter = cargoSorter;
        this.transportSorter = transportSorter;
    }

    @Override
    public void execute(CargoDataManager cargoDataManager, TransportDataManager transportDataManager) {
        log.info("Старт алгоритма плотной погрузки");
        transportValidator.validateTransportData(transportDataManager);
        List<Cargo> cargos = cargoSorter.sortCargosByWeight(cargoDataManager.getData());
        List<Transport> transports = transportSorter.sort(transportDataManager);
        for (Cargo cargo : cargos) {
            log.info("Погрузка груза: {}", cargo);
            tryLoadCargo(cargo, transports, transportDataManager);
        }
        log.info("Плотная погрузка окончена");
    }

    private void tryLoadCargo(Cargo cargo, List<Transport> transports, TransportDataManager transportDataManager) {
        for (Transport transport : transports) {
            try {
                cargoLoader.tryLoadToTransport(cargo, transport);
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