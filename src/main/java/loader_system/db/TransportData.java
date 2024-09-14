package loader_system.db;

import loader_system.model.entites.cargos.Cargo;
import loader_system.model.entites.transports.Transport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransportData implements Datable {

    private final List<Transport> transportData = new ArrayList<>();

    private final Map<Transport, List<Cargo>> transportCargo = new HashMap<>();

    @Override
    public List<Transport> getData() {
        return new ArrayList<>(transportData);
    }

    @Override
    public void add(Object o) {
        transportData.add((Transport) o);
        transportCargo.put((Transport) o, new ArrayList<>());
    }

    public void addCargoInTransport(Transport transport, Cargo cargo) {
        transportCargo.get(transport).add(cargo);
    }

    public int getCargoWeightInTransport(Transport transport) {
        int weight = 0;
        for(Cargo cargoInTransport : transportCargo.get(transport)) {
            weight += cargoInTransport.getWeight();
        }
        return weight;
    }

    @Override
    public void remove(Object o) {
        transportData.remove((Transport) o);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Transport transport : transportData)
            sb.append(transport.toString()).append("\n\n");
        return sb.toString();
    }
}
