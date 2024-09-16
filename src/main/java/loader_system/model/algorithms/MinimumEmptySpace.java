package loader_system.model.algorithms;

import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.entites.cargos.Cargo;
import loader_system.model.entites.transports.Transport;
import loader_system.model.exceptions.InvalidCargoSize;
import loader_system.model.exceptions.NoPlaceException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MinimumEmptySpace extends Algorithm {

    @Override
    public void execute(CargoData cargoData, TransportData transportData) {
        validateTransportData(transportData);
        log.debug("Executing MinimumEmptySpace algorithm");
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

    private List<Cargo> sortCargosByWeight(List<Cargo> cargos) {
        List<Cargo> sortedCargos = new ArrayList<>(cargos);
        sortedCargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        return sortedCargos;
    }
}