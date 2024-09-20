package loader.utils.initializers.transport;

import loader.model.entites.cargos.Cargo;
import loader.model.entites.transports.Transport;
import loader.model.entites.transports.Truck;
import loader.factories.transport.TruckFactory;
import loader.model.dto.TruckDto;
import loader.input.json.JsonService;
import loader.utils.CargoCounter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TruckInitializer {

    public List<Transport> initialize(int numberOfTransport) {
        List<Transport> transports = new ArrayList<>();
        for (int i = 0; i < numberOfTransport; i++) {
            transports.add(new Truck());
        }
        return transports;
    }

    public Map<Transport, List<Cargo>> initializeFromJson(String filepath) {
        List<TruckDto> truckDtos = new JsonService().read(TruckDto.class, filepath);
        Map<Transport, List<Cargo>> map = new HashMap<>();
        for (TruckDto truckDto : truckDtos) {
            map.put(
                    new TruckFactory().createTransport(truckDto.getBody()),
                    new ArrayList<>(truckDto.getCargos())
            );
        }
        return map;
    }
}
