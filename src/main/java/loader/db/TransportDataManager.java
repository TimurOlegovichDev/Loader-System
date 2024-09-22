package loader.db;

import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransportDataManager {

    private final Map<Transport, List<Cargo>> transportMap;

    public TransportDataManager(Map<Transport, List<Cargo>> transportMap) {
        this.transportMap = transportMap;
    }

    public List<Transport> getData() {
        return new ArrayList<>(transportMap.keySet());
    }

    public List<Cargo> getCargos(Transport transport) {
        return transportMap.get(transport);
    }

    public void add(Transport transport) {
        transportMap.put(transport, new ArrayList<>());
    }

    public void add(@NonNull List<Transport> transports) {
        transports.forEach(this::add);
    }

    public void add(Map<Transport, List<Cargo>> map) {
        transportMap.putAll(map);
    }

    public void addCargoInTransport(Transport transport, Cargo cargo) {
        transportMap.get(transport).add(cargo);
    }

    public int getCargoWeightInTransport(Transport transport) {
        int weight = 0;
        List<Cargo> cargos = getCargos(transport);
        if (cargos == null) {
            return weight;
        }
        for (Cargo cargoInTransport : cargos) {
            weight += cargoInTransport.getWeight();
        }
        return weight;
    }

    public void remove(Transport transport) {
        transportMap.remove(transport);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Transport transport : transportMap.keySet())
            sb.append(transport.toString()).append(System.lineSeparator());
        return sb.toString();
    }
}
