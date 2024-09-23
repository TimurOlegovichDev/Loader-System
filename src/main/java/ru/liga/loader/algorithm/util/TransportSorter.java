package ru.liga.loader.algorithm.util;

import ru.liga.loader.db.TransportDataManager;
import ru.liga.loader.model.entity.Transport;

import java.util.List;

public interface TransportSorter {

    List<Transport> sort(TransportDataManager transportDataManager);
}
