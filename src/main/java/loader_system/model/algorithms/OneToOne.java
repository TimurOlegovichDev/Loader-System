package loader_system.model.algorithms;

import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.entites.cargos.Cargo;
import loader_system.model.entites.transports.Transport;
import loader_system.model.exceptions.NoPlaceException;

import java.util.List;

public class OneToOne extends Algorithm {

    @Override
    public void execute(CargoData cargoData, TransportData transportData) {
        List<Cargo> cargos = cargoData.getData();
        cargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        List<Transport> transports = transportData.getData();
        for (Cargo cargo : cargos) {
            Transport transport = chooseTruckToLoad(transportData);
            tryFindEmptySpaceAndLoad(cargo, transport);
            transportData.addCargoInTransport(transport, cargo);
        }
    }

    private Transport chooseTruckToLoad(TransportData transportData) {
        if (transportData.getData().isEmpty()) {
            throw new NoPlaceException();
        }
        List<Transport> transports = transportData.getData();
        Transport truck = transports.get(0);
        for (int i = 1; i < transports.size(); i++) {
            if (transportData.getCargoWeightInTransport(transports.get(i))
                    <
                    transportData.getCargoWeightInTransport(truck)
            ) {
                truck = transportData.getData().get(i);
            }
        }
        return truck;
    }

}
