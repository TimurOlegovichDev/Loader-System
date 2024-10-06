package ru.liga.loader.factory.cargo;

import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.structure.CargoJsonStructure;

public interface CargoFactory {

    Cargo createCargo(String name, String form);

    Cargo createCargo(String name, char[][] form);

    Cargo createCargo(CargoJsonStructure cargoJsonStructure);
}
