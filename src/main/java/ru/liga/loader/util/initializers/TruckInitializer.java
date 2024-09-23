package ru.liga.loader.util.initializers;

import lombok.RequiredArgsConstructor;
import ru.liga.loader.factory.transport.TransportFactory;
import ru.liga.loader.factory.transport.TruckFactory;
import ru.liga.loader.model.entites.Cargo;
import ru.liga.loader.model.entites.Transport;
import ru.liga.loader.model.structures.TransportJsonStructure;
import ru.liga.loader.util.json.JsonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TruckInitializer {

    private final JsonService jsonService;

    public List<Transport> initialize(int numberOfTransport) {
        TransportFactory transportFactory = new TruckFactory();
        List<Transport> transports = new ArrayList<>();
        for (int i = 0; i < numberOfTransport; i++) {
            transports.add(
                    transportFactory.createTransport()
            );
        }
        return transports;
    }

    public Map<Transport, List<Cargo>> initializeFromJson(String filepath) {
        List<TransportJsonStructure> transportJsonStructures =
                jsonService.read(TransportJsonStructure.class, filepath);
        Map<Transport, List<Cargo>> map = new HashMap<>();
        for (TransportJsonStructure transportJsonStructure : transportJsonStructures) {
            map.put(
                    new TruckFactory().createTransport(
                            transportJsonStructure.getBody()
                    ),
                    new ArrayList<>(
                            transportJsonStructure.getCargos()
                    )
            );
        }
        return map;
    }
}
