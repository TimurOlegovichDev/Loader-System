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
            log.debug("Processing cargo: {}", cargo);
            boolean loaded = false;
            try {
                for (Transport transport : transportData.getData()) {
                   try {
                       tryFindEmptySpaceAndLoad(cargo, transport);
                       loaded = true;
                       break;
                   } catch (NoPlaceException e) {
                       log.debug(e.getMessage());
                   }
                }
            } catch (InvalidCargoSize e) {
                log.warn(e.getMessage());
                continue;
            }
            if (!loaded) {
                throw new NoPlaceException("There is no more space for this cargo: " + cargo);
            }
        }
        log.debug("MinimumEmptySpace algorithm execution completed");
    }

    private void validateTransportData(TransportData transportData) {
        if (transportData.getData().isEmpty()) {
            throw new NoPlaceException("There is no truck to load");
        }
    }

    private List<Cargo> sortCargosByWeight(List<Cargo> cargos) {
        List<Cargo> sortedCargos = new ArrayList<>(cargos);
        sortedCargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        return sortedCargos;
    }
}