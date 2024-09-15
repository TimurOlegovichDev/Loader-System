package loader_system.model.algorithms;

import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.entites.cargos.Cargo;
import loader_system.model.entites.transports.Transport;
import loader_system.model.exceptions.NoPlaceException;
import loader_system.model.factories.transport.TruckFactory;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
public class MinimumEmptySpace extends Algorithm {

    @Override
    public void execute(CargoData cargoData, TransportData transportData) {
        log.debug("Executing MinimumEmptySpace algorithm");
        List<Cargo> cargos = cargoData.getData();
        log.debug("Sorting cargos by weight in descending order");
        cargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        for (Cargo cargo : cargos) {
            log.debug("Processing cargo: {}", cargo);
            for (Transport transport : transportData.getData()) {
                try {
                    tryFindEmptySpaceAndLoad(cargo, transport);
                } catch (NoPlaceException e) {
                    Transport truck = new TruckFactory().createTransport();
                    log.debug("Loading cargo: {} into new truck", cargo);
                    truck.loadCargo(cargo, truck.getBody().length - 1, 0);
                    transportData.add(truck);
                    transportData.addCargoInTransport(truck, cargo);
                }
            }
        }
        log.debug("MinimumEmptySpace algorithm execution completed");
    }
}