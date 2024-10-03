package ru.liga.loader.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.TransportCrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DefaultCrudTransportRepository implements TransportCrudRepository {

    private final Map<Transport, List<Cargo>> transportMap;

    @Autowired
    public DefaultCrudTransportRepository(Map<Transport, List<Cargo>> transportMap) {
        this.transportMap = transportMap;
    }

    /**
     * Возвращает список всех грузов для всех транспортных средств.
     *
     * @return список грузов для всех транспортных средств
     */

    @Override
    public List<List<Cargo>> getAll() {
        return transportMap.values().stream().toList();
    }

    /**
     * Возвращает список всех транспортных средств.
     *
     * @return список транспортных средств
     */

    @Override
    public List<Transport> getKeys() {
        return transportMap.keySet().stream().toList();
    }

    /**
     * Возвращает грузы для указанного транспортного средства.
     *
     * @param key транспортное средство
     * @return грузы для указанного транспортного средства
     */

    @Override
    public List<Cargo> getBy(Transport key) {
        return transportMap.get(key);
    }

    /**
     * Добавляет несколько транспортных средств с их грузами в репозиторий.
     *
     * @param entities карта, содержащая транспортные средства с их грузами
     */

    @Override
    public void addAll(Map<Transport, List<Cargo>> entities) {
        transportMap.putAll(entities);
    }

    /**
     * Обновляет грузы для указанного транспортного средства.
     *
     * @param key   транспортное средство
     * @param value новые грузы
     * @return обновленные грузы
     */

    @Override
    public List<Cargo> update(Transport key, List<Cargo> value) {
        return transportMap.put(key, value);
    }

    /**
     * Добавляет транспортное средство в репозиторий.
     *
     * @param entity транспортное средство
     */

    @Override
    public void add(Transport entity) {
        transportMap.put(entity, new ArrayList<>());
    }

    /**
     * Добавляет несколько транспортных средств в репозиторий.
     *
     * @param entities список транспортных средств
     */

    @Override
    public void add(List<Transport> entities) {
        entities.forEach(this::add);
    }

    /**
     * Удаляет транспортное средство из репозитория.
     *
     * @param id транспортное средство
     * @return удаленные грузы
     */

    @Override
    public List<Cargo> delete(Transport id) {
        return transportMap.remove(id);
    }

    /**
     * Возвращает грузы для указанного транспортного средства.
     *
     * @param transport транспортное средство
     * @return грузы для указанного транспортного средства
     */

    @Override
    public List<Cargo> getCargos(Transport transport) {
        return transportMap.get(transport);
    }

    /**
     * Возвращает все грузы для всех транспортных средств.
     *
     * @return все грузы для всех транспортных средств
     */

    @Override
    public List<Cargo> getAllCargos() {
        return transportMap.keySet().stream()
                .flatMap(transport -> transportMap.get(transport)
                        .stream()
                )
                .toList();
    }

    /**
     * Добавляет груз в транспортное средство.
     *
     * @param transport транспортное средство
     * @param cargo     груз
     */

    @Override
    public void addCargoInTransport(Transport transport, Cargo cargo) {
        transportMap.get(transport).add(cargo);
    }

    /**
     * Выгружает все грузы из транспортных средств.
     */

    @Override
    public void unloadAllCargo() {
        transportMap.values().forEach(List::clear);
    }

    /**
     * Возвращает строковое представление репозитория.
     *
     * @return строковое представление репозитория
     */

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