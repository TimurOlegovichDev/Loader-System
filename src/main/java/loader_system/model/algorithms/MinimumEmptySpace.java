package loader_system.model.algorithms;

import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.entites.cargos.Cargo;
import loader_system.model.entites.transports.Transport;
import loader_system.model.exceptions.NoPlaceException;
import loader_system.model.factories.transport.TruckFactory;

import java.util.List;

public class MinimumEmptySpace extends Algorithm {

    @Override
    public void execute(CargoData cargoData, TransportData transportData) {
        List<Cargo> cargos = cargoData.getData();
        cargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        for (Cargo cargo : cargos) {
            for (Transport transport : transportData.getData()) {
                try {
                    tryFindEmptySpaceAndLoad(cargo, transport);
                } catch (NoPlaceException e) {
                    Transport truck = new TruckFactory().createTransport();
                    truck.loadCargo(cargo, truck.getBody().length - 1, 0);
                    transportData.addCargoInTransport(truck, cargo);
                    transportData.add(truck);
                }
            }
        }
    }

}
