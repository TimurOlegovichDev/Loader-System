package loader.db;

import loader.entites.cargos.Box;
import loader.entites.cargos.Cargo;
import loader.entites.transports.Transport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransportData {

    private final Map<Transport, List<Cargo>> transportMap = new HashMap<>();

    public List<Transport> getData() {
        return new ArrayList<>(transportMap.keySet());
    }

    public List<Cargo> getCargos(Transport transport) {
        return transportMap.get(transport);
    }

    public List<Box> getBoxes(Transport transport) {
        List<Box> boxes = new ArrayList<>();
        for (Cargo cargo : getCargos(transport)) {
            if(cargo instanceof Box) {
                boxes.add((Box) cargo);
            }
        }
        return boxes;
    }

    public void add(Object o) {
        transportMap.put((Transport) o, new ArrayList<>());
    }

    public void add(Map<Transport, List<Cargo>> map) {
        transportMap.putAll(map);
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
