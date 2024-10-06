package ru.liga.loader.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.liga.loader.model.entity.Cargo;

import java.util.List;
import java.util.UUID;

public interface CargoCrudRepository extends CrudRepository<Cargo, UUID> {

    Cargo findByName(String name);

    List<Cargo> findAllByTransportId(UUID uuidTransport);

    @Query("SELECT DISTINCT c FROM Cargo c")
    Iterable<Cargo> findAllUnique();
}
