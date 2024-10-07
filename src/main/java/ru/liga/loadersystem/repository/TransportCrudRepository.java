package ru.liga.loadersystem.repository;

import org.springframework.data.repository.CrudRepository;
import ru.liga.loadersystem.model.entity.Transport;

import java.util.UUID;

public interface TransportCrudRepository extends CrudRepository<Transport, UUID> {
}
