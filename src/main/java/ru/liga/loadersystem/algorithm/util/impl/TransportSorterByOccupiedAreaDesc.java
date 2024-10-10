package ru.liga.loadersystem.algorithm.util.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loadersystem.algorithm.util.TransportSorter;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.repository.TransportCrudRepository;
import ru.liga.loadersystem.service.TransportService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TransportSorterByOccupiedAreaDesc implements TransportSorter {

    private final TransportService transportService;

    /**
     * Сортирует список транспортных средств площади, занятой грузами в порядке убывания.
     *
     * @param transportDataRepository менеджер данных транспортных средств, из которого берется список транспортных средств
     * @return отсортированный список транспортных средств
     */
    @Override
    public List<Transport> sort(TransportCrudRepository transportDataRepository, List<Transport> transports) {
        log.debug("Сортировка транспорта по заполняемости в порядке убывания...");
        List<Transport> sorted = transports.stream()
                .sorted(Comparator.comparingInt(transportService::percentageOfOccupancy))
                .collect(Collectors.toList());
        Collections.reverse(sorted);
        return sorted;
    }
}
