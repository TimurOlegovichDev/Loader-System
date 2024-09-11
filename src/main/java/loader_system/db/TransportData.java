package loader_system.db;

import loader_system.model.entites.transports.Transport;

import java.util.ArrayList;
import java.util.List;

public class TransportData implements Datable {

    private final List<Transport> transportData = new ArrayList<>();

    @Override
    public List<Transport> getData() {
        return new ArrayList<>(transportData);
    }

    @Override
    public void add(Object o) {
        transportData.add((Transport) o);
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
