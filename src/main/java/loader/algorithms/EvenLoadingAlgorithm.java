package loader.algorithms;

import loader.algorithms.utils.CargoLoader;
import loader.algorithms.utils.CargoSorter;
import loader.algorithms.utils.TransportValidator;
import loader.db.CargoDataManager;
import loader.db.TransportDataManager;
import loader.exceptions.InvalidCargoSize;
import loader.exceptions.NoPlaceException;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class EvenLoadingAlgorithm extends LoadingCargoAlgorithm {

    private final CargoSorter cargoSorter;

    public EvenLoadingAlgorithm(CargoLoader cargoLoader,
                                CargoSorter cargoSorter,
                                TransportValidator transportValidator) {
        super(cargoLoader, transportValidator);
        this.cargoSorter = cargoSorter;
    }

    @Override
    public void execute(CargoDataManager cargoDataManager, TransportDataManager transportDataManager) {
        log.info("Старт алгоритма равномерной погрузки");
        transportValidator.validateTransportData(transportDataManager);
        List<Cargo> cargos = cargoSorter.sortCargosByWeight(cargoDataManager.getData());
        for (Cargo cargo : cargos) {
            log.info("Погрузка груза: {}", cargo);
            try {
                Optional<Transport> optional = findMostFreeTransport(transportDataManager);
                optional.ifPresentOrElse(
                        transport -> {
                            cargoLoader.tryLoadToTransport(cargo, transport);
                            transportDataManager.addCargoInTransport(transport, cargo);
                        },
                        () -> {
                            throw new NoPlaceException("Нет грузовика для погрузки груза: " + cargo);
                        }
                );
                log.debug("Груз успешно погружен: {}", cargo);
            } catch (InvalidCargoSize e) {
                log.warn(e.getMessage());
            }
        }
        log.info("Равномерная погрузка окончена");
    }

    private Optional<Transport> findMostFreeTransport(TransportDataManager transportDataManager) {
        log.debug("Поиск максимально незагруженного транспорта");
        List<Transport> transports = transportDataManager.getData();
        Transport truck = null;
        for (int i = 0; i < transports.size(); i++) {
            int anotherWeight = transportDataManager.getCargoWeightInTransport(transports.get(i));
            int currentWeight = transportDataManager.getCargoWeightInTransport(truck);
            log.trace("Сравнение транспорта с весом: {} и {}", currentWeight, anotherWeight);
            if (truck == null || anotherWeight <= currentWeight) {
                truck = transportDataManager.getData().get(i);
            }
        }
        return Optional.ofNullable(truck);
    }

}
