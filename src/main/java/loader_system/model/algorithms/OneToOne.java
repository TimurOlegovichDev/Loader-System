package loader_system.model.algorithms;

import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.entites.cargos.Cargo;
import loader_system.model.entites.transports.Transport;
import loader_system.model.factories.transport.TruckFactory;

import java.util.List;

public class OneToOne implements Algorithm {

    @Override
    public void execute(CargoData cargoData, TransportData transportData) {
        List<Cargo> cargos = cargoData.getData();
        cargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        for(Cargo cargo : cargos){
            Transport truck = new TruckFactory().createTransport();
            truck.loadCargo(cargo, truck.getBody().length-1,0);
            transportData.add(truck);
        }
    }
}
