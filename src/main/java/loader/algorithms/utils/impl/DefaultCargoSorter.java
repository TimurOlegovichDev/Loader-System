package loader.algorithms.utils.impl;

import loader.algorithms.utils.CargoSorter;
import loader.model.entites.Cargo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DefaultCargoSorter implements CargoSorter {
    @Override
    public List<Cargo> sortCargosByWeight(List<Cargo> cargos) {
        log.debug("Сортировка груза по весу...");
        List<Cargo> sortedCargos = new ArrayList<>(cargos);
        sortedCargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        return sortedCargos;
    }
}