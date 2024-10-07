package ru.liga.loadersystem.algorithm.util.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.loadersystem.algorithm.util.CargoSorter;
import ru.liga.loadersystem.model.entity.Cargo;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DefaultCargoSorter implements CargoSorter {

    /**
     * Сортирует список грузов по весу в порядке убывания.
     * Этот метод создает копию списка грузов, сортирует ее по весу грузов в порядке убывания
     * и возвращает отсортированный список.
     *
     * @param cargos список грузов, который будет отсортирован
     * @return отсортированный список грузов
     */

    @Override
    public List<Cargo> sort(List<Cargo> cargos) {
        log.debug("Сортировка груза по весу...");
        List<Cargo> sortedCargos = new ArrayList<>(cargos);
        sortedCargos.sort((x, y) -> Integer.compare(y.getArea(), x.getArea()));
        return sortedCargos;
    }
}