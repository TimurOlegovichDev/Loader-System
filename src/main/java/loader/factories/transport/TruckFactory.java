package loader.factories.transport;

import loader.entites.transports.Transport;
import loader.entites.transports.Truck;

public class TruckFactory implements TransportFactory {

    @Override
    public Transport createTransport() {
        return new Truck();
    }

    @Override
    public Transport createTransport(char[][] body) {
        return new Truck(body);
    }
    
}
