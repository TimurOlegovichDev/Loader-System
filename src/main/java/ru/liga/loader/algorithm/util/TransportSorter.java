package ru.liga.loader.algorithm.util;

import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.impl.DefaultCrudTransportRepository;

import java.util.List;

public interface TransportSorter {

    List<Transport> sort(DefaultCrudTransportRepository transportDataRepository);
}
