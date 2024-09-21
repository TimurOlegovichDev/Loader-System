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

@Slf4j
public class MinimumEmptySpaceAlgorithm extends LoadingCargoAlgorithm {

    public MinimumEmptySpaceAlgorithm(CargoLoader cargoLoader, CargoSorter cargoSorter, TransportValidator transportValidator) {
        super(cargoLoader, cargoSorter, transportValidator);
    }

    @Override
    public void execute(CargoData cargoData, TransportData transportData) {
        log.debug("Executing MinimumEmptySpace algorithm");
        transportValidator.validateTransportData(transportData);
        List<Cargo> cargos = cargoSorter.sortCargosByWeight(cargoData.getData());
        for (Cargo cargo : cargos) {
            log.info("Processing cargo: {}", cargo);
            tryLoadCargo(cargo, transportData);
        }
        log.debug("MinimumEmptySpace algorithm execution completed");
    }

    private void tryLoadCargo(Cargo cargo, TransportData transportData) {
        for (Transport transport : transportData.getData()) {
            try {
                cargoLoader.tryLoadToTransport(cargo, transport);
                transportData.addCargoInTransport(transport, cargo);
                log.info("Load cargo completed: {}", cargo);
                return;
            } catch (NoPlaceException e) {
                log.debug(e.getMessage());
            } catch (InvalidCargoSize i) {
                log.debug(i.getMessage());
                return;
            }
        }
        throw new NoPlaceException("Cant find transport to load this cargo: " + cargo);
    }
}