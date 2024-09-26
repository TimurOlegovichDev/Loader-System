package ru.liga.loader.algorithm.util.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.algorithm.util.TransportSorter;
import ru.liga.loader.db.TransportDataManager;
import ru.liga.loader.model.entity.Transport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TransportSorterByWeightDesc implements TransportSorter {

    /**
     * Сортирует список транспортных средств по весу груза в порядке убывания.
     * Этот метод создает копию списка транспортных средств, полученного из менеджера данных транспортных средств,
     * сортирует ее по весу груза в порядке возрастания и возвращает отсортированный список.
     *
     * @param transportDataManager менеджер данных транспортных средств, из которого берется список транспортных средств
     * @return отсортированный список транспортных средств
     */

    @Override
    public List<Transport> sort(TransportDataManager transportDataManager) {
        log.debug("Сортировка транспорта по весу груза в порядке убывания...");
        List<Transport> sorted = new ArrayList<>(transportDataManager.getData()).stream()
                .sorted(Comparator.comparingInt(transportDataManager::getCargoWeightInTransport))
                .collect(Collectors.toList());
        Collections.reverse(sorted);
        return sorted;
    }
}
