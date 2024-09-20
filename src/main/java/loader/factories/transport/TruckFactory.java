package loader.factories.transport;

import loader.model.entites.transports.Transport;

public class TruckFactory implements TransportFactory {

    @Override
    public Transport createTransport() {
        return new Transport();
    }

    @Override
    public Transport createTransport(char[][] body) {
        return new Transport(body);
    }

}
