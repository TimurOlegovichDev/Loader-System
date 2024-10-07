package ru.liga.loadersystem.algorithm.util;

import ru.liga.loadersystem.model.entity.Cargo;

import java.util.List;

public interface CargoSorter {

    List<Cargo> sort(List<Cargo> cargos);
}
