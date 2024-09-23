package ru.liga.loader.algorithm.util.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.algorithm.util.CargoSorter;
import ru.liga.loader.model.entites.Cargo;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DefaultCargoSorter implements CargoSorter {

    @Override
    public List<Cargo> sort(List<Cargo> cargos) {
        log.debug("Сортировка груза по весу...");
        List<Cargo> sortedCargos = new ArrayList<>(cargos);
        sortedCargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        return sortedCargos;
    }
}