package ru.liga.loadersystem.factory.transport;

import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.model.json.TransportJsonStructure;

public interface TransportFactory {

    Transport createTransport();

    Transport createTransport(int width, int height);

    Transport createTransport(String id, String body);

    Transport createTransport(TransportJsonStructure transportJsonStructure);
}
