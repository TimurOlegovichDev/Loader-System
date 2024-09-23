package ru.liga.loader.factory.transport;

import ru.liga.loader.model.entites.Transport;

public interface TransportFactory {

    Transport createTransport();

    Transport createTransport(char[][] body);
}
