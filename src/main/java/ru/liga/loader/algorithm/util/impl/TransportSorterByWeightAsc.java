package ru.liga.loader.algorithm.util.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.algorithm.util.TransportSorter;
import ru.liga.loader.db.TransportDataManager;
import ru.liga.loader.model.entity.Transport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TransportSorterByWeightAsc implements TransportSorter {

    @Override
    public List<Transport> sort(TransportDataManager transportDataManager) {
        log.debug("Сортировка транспорта по весу груза в порядке возрастания...");
        return new ArrayList<>(transportDataManager.getData()).stream()
                .sorted(Comparator.comparingInt(transportDataManager::getCargoWeightInTransport))
                .collect(Collectors.toList());
    }
}
