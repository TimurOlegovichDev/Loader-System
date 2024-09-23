package ru.liga.loader.algorithm.util;

import ru.liga.loader.model.entites.Cargo;

import java.util.List;

public interface CargoSorter {

    List<Cargo> sort(List<Cargo> cargos);
}
