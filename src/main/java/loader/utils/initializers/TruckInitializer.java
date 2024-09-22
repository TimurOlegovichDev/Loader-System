package loader.utils.initializers;

import loader.factories.transport.TransportFactory;
import loader.factories.transport.TruckFactory;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import loader.model.structures.TransportJsonStructure;
import loader.utils.json.JsonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TruckInitializer {

    private final JsonService jsonService;

    public TruckInitializer(JsonService jsonService) {
        this.jsonService = jsonService;
    }

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
