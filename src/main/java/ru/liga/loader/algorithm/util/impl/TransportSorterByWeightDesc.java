package ru.liga.loader.algorithm.util.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loader.algorithm.util.TransportSorter;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.service.TransportRepositoryService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TransportSorterByWeightDesc implements TransportSorter {

    private final TransportRepositoryService transportRepositoryService;

    @Autowired
    public TransportSorterByWeightDesc(TransportRepositoryService transportRepositoryService) {
        this.transportRepositoryService = transportRepositoryService;
    }

    /**
     * Сортирует список транспортных средств по весу груза в порядке убывания.
     * Этот метод создает копию списка транспортных средств, полученного из менеджера данных транспортных средств,
     * сортирует ее по весу груза в порядке возрастания и возвращает отсортированный список.
     *
     * @param transportDataRepository менеджер данных транспортных средств, из которого берется список транспортных средств
     * @return отсортированный список транспортных средств
     */

    @Override
    public List<Transport> sort(TransportCrudRepository transportDataRepository, List<Transport> transports) {
        log.debug("Сортировка транспорта по весу груза в порядке убывания...");
        List<Transport> sorted = transports.stream()
                .sorted(Comparator.comparingInt(transportRepositoryService::percentageOfOccupancy))
                .collect(Collectors.toList());
        Collections.reverse(sorted);
        return sorted;
    }
}
