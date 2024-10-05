package ru.liga.loader.algorithm.util.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loader.algorithm.util.TransportSorter;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.service.TransportRepositoryService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TransportSorterByOccupiedAreaAsc implements TransportSorter {

    private final TransportRepositoryService transportRepositoryService;

    @Autowired
    public TransportSorterByOccupiedAreaAsc(TransportRepositoryService transportRepositoryService) {
        this.transportRepositoryService = transportRepositoryService;
    }

    /**
     * Сортирует список транспортных средств площади, занятой грузами в порядке возрастания.
     *
     * @param transportDataRepository менеджер данных транспортных средств, из которого берется список транспортных средств
     * @return отсортированный список транспортных средств
     */

    @Override
    public List<Transport> sort(TransportCrudRepository transportDataRepository, List<Transport> transports) {
        log.debug("Сортировка транспорта по заполненности в порядке возрастания...");
        return transports.stream()
                .sorted(Comparator.comparingInt(transportRepositoryService::percentageOfOccupancy))
                .collect(Collectors.toList());
    }
}
