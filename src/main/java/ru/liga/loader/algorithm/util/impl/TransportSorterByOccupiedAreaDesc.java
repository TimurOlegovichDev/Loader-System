package ru.liga.loader.algorithm.util.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loader.algorithm.util.TransportSorter;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.service.TransportService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TransportSorterByOccupiedAreaDesc implements TransportSorter {

    private final TransportService transportServiceq11;

    @Autowired
    public TransportSorterByOccupiedAreaDesc(TransportService transportServiceq11) {
        this.transportServiceq11 = transportServiceq11;
    }

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
                .sorted(Comparator.comparingInt(transportServiceq11::percentageOfOccupancy))
                .collect(Collectors.toList());
        Collections.reverse(sorted);
        return sorted;
    }
}
