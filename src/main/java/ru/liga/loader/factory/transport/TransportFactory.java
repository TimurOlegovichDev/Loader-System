package ru.liga.loader.factory.transport;

import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportJsonStructure;

public interface TransportFactory {

    Transport createTransport();

    Transport createTransport(int width, int height);

    Transport createTransport(String id, String body);

    Transport createTransport(TransportJsonStructure transportJsonStructure);
}
