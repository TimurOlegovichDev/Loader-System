package ru.liga.loadersystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.liga.loadersystem.model.entity.Transport;

import java.util.UUID;

@Repository
public interface TransportCrudRepository extends CrudRepository<Transport, UUID> {
}
