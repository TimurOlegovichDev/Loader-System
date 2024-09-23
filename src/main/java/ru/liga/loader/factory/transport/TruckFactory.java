package ru.liga.loader.factory.transport;

import ru.liga.loader.model.entity.Transport;

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
