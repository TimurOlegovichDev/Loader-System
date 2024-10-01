package ru.liga.loader.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.TransportCrudRepository;

import java.util.*;

@Repository
public class DefaultCrudTransportRepository implements TransportCrudRepository {

    private final Map<Transport, List<Cargo>> transportMap;

    @Autowired
    public DefaultCrudTransportRepository(Map<Transport, List<Cargo>> transportMap) {
        this.transportMap = transportMap;
    }

    @Override
    public List<List<Cargo>> getAll() {
        return transportMap.values().stream().toList();
    }

    @Override
    public Set<Transport> getKeys() {
        return transportMap.keySet();
    }

    @Override
    public List<Cargo> getBy(Transport key) {
        return transportMap.get(key);
    }

    @Override
    public void addAll(Map<Transport, List<Cargo>> entities) {
        transportMap.putAll(entities);
    }

    @Override
    public List<Cargo> update(Transport key, List<Cargo> value) {
        return transportMap.put(key, value);
    }

    @Override
    public void add(Transport entity) {
        transportMap.put(entity, new ArrayList<>());
    }

    @Override
    public void add(List<Transport> entities) {
        entities.forEach(this::add);
    }

    @Override
    public List<Cargo> delete(Transport id) {
        return transportMap.remove(id);
    }

    @Override
    public List<Cargo> getCargos(Transport transport) {
        return transportMap.get(transport);
    }

    @Override
    public List<Cargo> getAllCargos() {
        List<Cargo> cargos = new ArrayList<>();
        for (Transport transport : transportMap.keySet()) {
            cargos.addAll(getCargos(transport));
        }
        return cargos;
    }

    @Override
    public void addCargoInTransport(Transport transport, Cargo cargo) {
        transportMap.get(transport).add(cargo);
    }

    @Override
    public int percentageOfOccupancy(Transport transport) {
        int bodyArea = transport.getBody().length * transport.getBody()[0].length;
        int cargoArea = 0;
        List<Cargo> cargos = getCargos(transport);
        if (cargos == null || bodyArea <= 0) {
            return cargoArea;
        }
        for (Cargo cargoInTransport : cargos) {
            cargoArea += cargoInTransport.getArea();
        }
        return cargoArea * 100 / bodyArea;
    }

    @Override
    public void removeCargo(Transport transport, Cargo cargo) {
        transportMap.get(transport).remove(cargo);
    }

    @Override
    public Optional<Transport> getTransportById(String id) {
        for (Transport transport : transportMap.keySet()) {
            if (transport.getId().equals(id)) {
                return Optional.of(transport);
            }
        }
        return Optional.empty();
    }

    @Override
    public void removeAllCargo(Transport transport) {
        transportMap.get(transport).clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Transport transport : transportMap.keySet()) {
            sb.append(System.lineSeparator())
                    .append(transport);
        }
        return sb.toString();
    }
}