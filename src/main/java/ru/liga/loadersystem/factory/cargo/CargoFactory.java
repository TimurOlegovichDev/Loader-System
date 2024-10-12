package ru.liga.loadersystem.factory.cargo;

import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.json.CargoJsonStructure;

public interface CargoFactory {

    Cargo createCargo(String name, String form);

    Cargo createCargo(String name, char[][] form);

    Cargo createCargo(CargoJsonStructure cargoJsonStructure);
}
