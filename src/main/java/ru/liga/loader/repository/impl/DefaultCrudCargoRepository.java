package ru.liga.loader.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.repository.CargoCrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DefaultCrudCargoRepository implements CargoCrudRepository {

    private final Map<String, Cargo> cargoData;

    @Autowired
    public DefaultCrudCargoRepository(Map<String, Cargo> cargoData) {
        this.cargoData = cargoData;
    }

    @Override
    public List<Cargo> getAll() {
        return new ArrayList<>(cargoData.values());
    }

    @Override
    public List<String> getKeys() {
        return cargoData.keySet().stream().toList();
    }

    @Override
    public Cargo getBy(String key) {
        return cargoData.get(key);
    }

    @Override
    public void addAll(Map<String, Cargo> entities) {
        cargoData.putAll(entities);
    }

    @Override
    public Cargo update(String key, Cargo value) {
        return cargoData.put(key, value);
    }

    @Override
    public Cargo delete(String id) {
        return cargoData.remove(id);
    }

    @Override
    public Cargo put(Cargo cargo) {
        return cargoData.put(cargo.getName(), cargo);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Cargo cargo : cargoData.values()) {
            builder.append(cargo);
        }
        return builder.toString();
    }
}