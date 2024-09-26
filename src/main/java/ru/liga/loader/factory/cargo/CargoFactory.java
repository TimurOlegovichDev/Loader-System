package ru.liga.loader.factory.cargo;

import ru.liga.loader.model.entity.Cargo;

public interface CargoFactory {

    Cargo createCargo(char[][] form);
}
