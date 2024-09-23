package ru.liga.loader.factory.cargo;

import ru.liga.loader.model.entity.Cargo;

public class DefaultCargoFactory implements CargoFactory {

    @Override
    public Cargo createCargo(char[][] form) {
        return new Cargo(form);
    }
}
