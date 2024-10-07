package ru.liga.loadersystem.algorithm.util;

import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.repository.TransportCrudRepository;

import java.util.List;

public interface TransportSorter {

    List<Transport> sort(
            TransportCrudRepository transportDataRepository,
            List<Transport> transports
    );
}
