package ru.liga.loader.factory.transport;

import ru.liga.loader.model.entity.Transport;

public interface TransportFactory {

    Transport createTransport();

    Transport createTransport(char[][] body);
}
