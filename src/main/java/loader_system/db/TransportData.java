package loader_system.db;

import loader_system.model.entites.cargos.Cargo;
import loader_system.model.entites.transports.Transport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransportData implements Datable {

    private final Map<Transport, List<Cargo>> transportMap = new HashMap<>();

    @Override
    public List<Transport> getData() {
        return new ArrayList<>(transportMap.keySet());
    }

    @Override
    public void add(Object o) {
        transportMap.put((Transport) o, new ArrayList<>());
    }

    public void addCargoInTransport(Transport transport, Cargo cargo) {
        transportMap.get(transport).add(cargo);
    }

    public int getCargoWeightInTransport(Transport transport) {
        int weight = 0;
        for(Cargo cargoInTransport : transportMap.get(transport)) {
            weight += cargoInTransport.getWeight();
        }
        return weight;
    }

    @Override
    public void remove(Object o) {
        transportMap.remove((Transport) o);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Transport transport : transportMap.keySet())
            sb.append(transport.toString()).append("\n\n");
        return sb.toString();
    }
}
