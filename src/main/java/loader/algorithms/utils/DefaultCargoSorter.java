package loader.algorithms.utils;

import loader.model.entites.Cargo;

import java.util.ArrayList;
import java.util.List;

public class DefaultCargoSorter implements CargoSorter {
    @Override
    public List<Cargo> sortCargosByWeight(List<Cargo> cargos) {
        List<Cargo> sortedCargos = new ArrayList<>(cargos);
        sortedCargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        return sortedCargos;
    }
}