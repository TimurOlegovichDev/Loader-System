package loader_system.model.factories.transport;

import loader_system.model.entites.transports.Transport;
import loader_system.model.entites.transports.Truck;

import java.util.ArrayList;
import java.util.List;

public class TruckFactory implements TransportFactory {

    @Override
    public Transport createTransport() {
        return new Truck();
    }

    public List<Transport> createTransport(int numberOfTrucks) {
        List<Transport> transports = new ArrayList<>();
        for (int i = 0; i < numberOfTrucks; i++) {
            transports.add(createTransport());
        }
        return transports;
    }
    
}
