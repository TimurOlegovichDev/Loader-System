package loader.factories.transport;

import loader.entites.transports.Transport;

public interface TransportFactory {

    Transport createTransport();

    Transport createTransport(char[][] body);
}
