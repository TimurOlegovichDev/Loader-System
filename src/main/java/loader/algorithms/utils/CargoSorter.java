package loader.algorithms.utils;

import loader.model.entites.Cargo;

import java.util.List;

public interface CargoSorter {

    List<Cargo> sortCargosByWeight(List<Cargo> cargos);
}