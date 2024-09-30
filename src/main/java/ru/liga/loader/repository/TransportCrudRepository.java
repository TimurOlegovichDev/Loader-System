package ru.liga.loader.repository;

import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;

import java.util.List;

public interface TransportCrudRepository extends CrudRepository<Transport, List<Cargo>> {

    void add(List<Transport> entities);

    List<Cargo> getCargos(Transport transport);

    void addCargoInTransport(Transport transport, Cargo cargo);

    void add(Transport transport);

    int getCargoAreaInTransport(Transport transport);
}