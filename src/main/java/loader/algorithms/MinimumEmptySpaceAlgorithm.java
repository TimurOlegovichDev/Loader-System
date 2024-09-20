package loader.algorithms;

import loader.db.CargoData;
import loader.db.TransportData;
import loader.model.entites.cargos.Cargo;
import loader.model.entites.transports.Transport;
import loader.exceptions.InvalidCargoSize;
import loader.exceptions.NoPlaceException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MinimumEmptySpaceAlgorithm extends LoadingCargoAlgorithm {

    @Override
    public void execute(CargoData cargoData, TransportData transportData) {
        log.debug("Executing MinimumEmptySpace algorithm");
        validateTransportData(transportData);
        List<Cargo> cargos = sortCargosByWeight(cargoData.getData());
        for (Cargo cargo : cargos) {
            log.info("Processing cargo: {}", cargo);
            tryLoadCargo(cargo, transportData);
        }
        log.debug("MinimumEmptySpace algorithm execution completed");
    }

    private void tryLoadCargo(Cargo cargo, TransportData transportData) {
        for (Transport transport : transportData.getData()) {
            try {
                tryLoadToTransport(cargo, transport);
                transportData.addCargoInTransport(transport, cargo);
                log.info("Load cargo completed: {}", cargo);
                return;
            } catch (NoPlaceException e) {
                log.debug(e.getMessage());
            } catch (InvalidCargoSize i){
                log.debug(i.getMessage());
                return;
            }
        }
        throw new NoPlaceException("Cant find transport to load this cargo: " + cargo);
    }
}