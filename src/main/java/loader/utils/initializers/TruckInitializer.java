package loader.utils.initializers;

import loader.factories.transport.TruckFactory;
import loader.model.dto.TransportDto;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
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
        List<Transport> transports = new ArrayList<>();
        for (int i = 0; i < numberOfTransport; i++) {
            transports.add(new Transport());
        }
        return transports;
    }

    public Map<Transport, List<Cargo>> initializeFromJson(String filepath) {
        List<TransportDto> transportDtos = jsonService.read(TransportDto.class, filepath);
        Map<Transport, List<Cargo>> map = new HashMap<>();
        for (TransportDto transportDto : transportDtos) {
            map.put(
                    new TruckFactory().createTransport(transportDto.getBody()),
                    new ArrayList<>(transportDto.getCargos())
            );
        }
        return map;
    }
}
