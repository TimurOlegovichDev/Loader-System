package ru.liga.loader.factory.cargo;

import org.springframework.stereotype.Component;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.structure.CargoJsonStructure;

@Component
public class DefaultCargoFactory implements CargoFactory {

    /**
     * Создает груз по заданной форме.
     * Этот метод создает экземпляр груза по заданной форме и возвращает его.
     *
     * @param form форма груза
     * @return созданный груз
     */

    @Override
    public Cargo createCargo(String name, char[][] form) {
        return new Cargo(name, form);
    }

    /**
     * Создает груз по заданной форме, полученной из json файла
     *
     * @param cargoJsonStructure структура, полученная из файла
     * @return созданный груз
     */

    @Override
    public Cargo createCargo(CargoJsonStructure cargoJsonStructure) {
        return new Cargo(cargoJsonStructure);
    }
}
