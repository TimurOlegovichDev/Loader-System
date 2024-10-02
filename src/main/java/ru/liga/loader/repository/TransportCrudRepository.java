package ru.liga.loader.repository;

import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;

import java.util.List;
import java.util.Optional;

public interface TransportCrudRepository extends CrudRepository<Transport, List<Cargo>> {

    void add(List<Transport> entities);

    List<Cargo> getCargos(Transport transport);

    List<Cargo> getAllCargos();

    void addCargoInTransport(Transport transport, Cargo cargo);

    void add(Transport transport);

    int percentageOfOccupancy(Transport transport);

    Optional<Transport> getTransportById(String id);

    void updateCargosName(String lastName, String cargoName);

    void unloadAllCargo();
}