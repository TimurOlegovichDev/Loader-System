package ru.liga.loader.repository;

import org.springframework.data.repository.CrudRepository;
import ru.liga.loader.model.entity.Transport;

import java.util.UUID;

public interface TransportCrudRepository extends CrudRepository<Transport, UUID> {
}
