package loader.model.factories.transport;

import loader.model.entites.transports.Transport;

public interface TransportFactory {

    Transport createTransport();

    Transport createTransport(char[][] body);
}
