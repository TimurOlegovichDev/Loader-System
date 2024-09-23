package ru.liga.loader.factory.cargo;

import ru.liga.loader.model.entites.Cargo;

public interface CargoFactory {

    Cargo createCargo(char[][] form);
}
