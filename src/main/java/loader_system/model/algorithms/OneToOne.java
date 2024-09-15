package loader_system.model.algorithms;

import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.entites.cargos.Cargo;
import loader_system.model.entites.transports.Transport;
import loader_system.model.exceptions.NoPlaceException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OneToOne extends Algorithm {

    @Override
    public void execute(CargoData cargoData, TransportData transportData) {
        List<Cargo> cargos = cargoData.getData();
        cargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        for (Cargo cargo : cargos) {
            try {
                Transport transport = chooseTruckToLoad(transportData);
                tryFindEmptySpaceAndLoad(cargo, transport);
                transportData.addCargoInTransport(transport, cargo);
            } catch (NoPlaceException e) {
                log.error(e.getMessage());
            }
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
