package loader_system.model.factories.transport;

import loader_system.model.entites.transports.Transport;
import loader_system.model.entites.transports.Truck;

public class TruckFactory implements TransportFactory {

    @Override
    public Transport createTransport() {
        return new Truck();
    }
    
}
