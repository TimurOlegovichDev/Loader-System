package ru.liga.loader.factory.cargo;

import ru.liga.loader.model.entity.Cargo;

public class DefaultCargoFactory implements CargoFactory {

    /**
     * Создает груз по заданной форме.
     * Этот метод создает экземпляр груза по заданной форме и возвращает его.
     *
     * @param form форма груза
     * @return созданный груз
     */

    @Override
    public Cargo createCargo(char[][] form) {
        return new Cargo(form);
    }
}
