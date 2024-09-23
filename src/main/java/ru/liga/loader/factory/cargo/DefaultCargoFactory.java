package ru.liga.loader.factory.cargo;

import ru.liga.loader.model.entites.Cargo;

public class DefaultCargoFactory implements CargoFactory {

    @Override
    public Cargo createCargo(char[][] form) {
        return new Cargo(form);
    }
}
