package loader.model.algorithms;

import loader.db.CargoData;
import loader.db.TransportData;
import loader.model.entites.cargos.Cargo;
import loader.model.entites.transports.Transport;
import loader.model.exceptions.InvalidCargoSize;
import loader.model.exceptions.NoPlaceException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MinimumEmptySpace extends Algorithm {

    @Override
    public void execute(CargoData cargoData, TransportData transportData) {
        log.debug("Executing MinimumEmptySpace algorithm");
        validateTransportData(transportData);
        List<Cargo> cargos = sortCargosByWeight(cargoData.getData());
        for (Cargo cargo : cargos) {
            log.info("Processing cargo: {}", cargo);
            findTransportAndLoadCargo(cargo, transportData);
        }
        log.debug("MinimumEmptySpace algorithm execution completed");
    }

    private void findTransportAndLoadCargo(Cargo cargo, TransportData transportData) {
        for (Transport transport : transportData.getData()) {
            try {
                findEmptySpaceAndLoad(cargo, transport);
                transportData.addCargoInTransport(transport, cargo);
                log.info("Load cargo completed: {}", cargo);
                break;
            } catch (NoPlaceException e) {
                log.debug(e.getMessage());
            } catch (InvalidCargoSize i){
                log.debug(i.getMessage());
                break;
            }
        }
    }
}