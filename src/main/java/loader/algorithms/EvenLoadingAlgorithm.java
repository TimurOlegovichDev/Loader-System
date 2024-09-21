package loader.algorithms;

import loader.algorithms.utils.CargoLoader;
import loader.algorithms.utils.CargoSorter;
import loader.algorithms.utils.TransportValidator;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.exceptions.InvalidCargoSize;
import loader.exceptions.NoPlaceException;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class EvenLoadingAlgorithm extends LoadingCargoAlgorithm {

    public EvenLoadingAlgorithm(CargoLoader cargoLoader,
                                CargoSorter cargoSorter,
                                TransportValidator transportValidator) {
        super(cargoLoader, cargoSorter, transportValidator);
    }

    @Override
    public void execute(CargoData cargoData, TransportData transportData) {
        log.debug("Executing EvenLoading algorithm");
        transportValidator.validateTransportData(transportData);
        List<Cargo> cargos = cargoSorter.sortCargosByWeight(cargoData.getData());
        for (Cargo cargo : cargos) {
            log.info("Processing cargo: {}", cargo);
            try {
                Optional<Transport> optional = findMostFreeTransport(transportData);
                optional.ifPresentOrElse(
                        transport -> {
                            cargoLoader.tryLoadToTransport(cargo, transport);
                            transportData.addCargoInTransport(transport, cargo);
                        },
                        () -> {
                            throw new NoPlaceException("No place found for cargo: " + cargo);
                        }
                );
                log.info("Load cargo completed: {}", cargo);
            } catch (InvalidCargoSize e) {
                log.warn(e.getMessage());
            }
        }
        log.debug("EvenLoading algorithm finished");
    }

    private Optional<Transport> findMostFreeTransport(TransportData transportData) {
        List<Transport> transports = transportData.getData();
        Transport truck = null;
        for (int i = 0; i < transports.size(); i++) {
            int anotherWeight = transportData.getCargoWeightInTransport(transports.get(i));
            int currentWeight = transportData.getCargoWeightInTransport(truck);
            log.debug(anotherWeight + " - " + currentWeight);
            if (truck == null || anotherWeight <= currentWeight) {
                truck = transportData.getData().get(i);
            }
        }
        return Optional.ofNullable(truck);
    }

}
