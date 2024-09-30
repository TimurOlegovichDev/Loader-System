package ru.liga.loader.algorithm.util.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.loader.algorithm.util.TransportSorter;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.TransportCrudRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TransportSorterByWeightAsc implements TransportSorter {

    /**
     * Сортирует список транспортных средств по весу груза в порядке возрастания.
     * Этот метод создает копию списка транспортных средств, полученного из менеджера данных транспортных средств,
     * сортирует ее по весу груза в порядке возрастания и возвращает отсортированный список.
     *
     * @param transportDataRepository менеджер данных транспортных средств, из которого берется список транспортных средств
     * @return отсортированный список транспортных средств
     */

    @Override
    public List<Transport> sort(TransportCrudRepository transportDataRepository) {
        log.debug("Сортировка транспорта по весу груза в порядке возрастания...");
        return new ArrayList<>(transportDataRepository.getKeys()).stream()
                .sorted(Comparator.comparingInt(transportDataRepository::getCargoAreaInTransport))
                .collect(Collectors.toList());
    }
}
