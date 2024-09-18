package loader.model.utils.initializers.transport;

import loader.model.dto.transport.TruckDto;
import loader.model.entites.cargos.Box;
import loader.model.entites.cargos.Cargo;
import loader.model.entites.transports.Transport;
import loader.model.entites.transports.Truck;
import loader.model.factories.transport.TransportFactory;
import loader.model.factories.transport.TruckFactory;
import loader.model.utils.json.JsonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TruckInitializer implements TransportInitializer {

    @Override
    public List<Transport> initializeToList(int numberOfTransport) {
        List<Transport> transports = new ArrayList<>();
        for (int i = 0; i < numberOfTransport; i++) {
            transports.add(new Truck());
        }
        return transports;
    }

    @Override
    public Map<Transport, List<Cargo>> initializeToMapWithCargoFromFile(String filepath) {
        List<TruckDto> truckDtos = new JsonService().readToList(TruckDto.class, filepath);
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
