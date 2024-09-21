package loader.factories.transport;

import loader.model.entites.Transport;

public interface TransportFactory {

    Transport createTransport();

    Transport createTransport(char[][] body);
}
