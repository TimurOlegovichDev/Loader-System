package ru.liga.loadersystem.factory.cargo;

import org.springframework.stereotype.Component;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.structure.CargoJsonStructure;

import java.util.Arrays;

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
    public Cargo createCargo(String name, String form) {
        return new Cargo(name, form);
    }

    @Override
    public Cargo createCargo(String name, char[][] form) {
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(form).forEach((chars) -> stringBuilder.append(new String(chars)).append(";"));
        return new Cargo(name, stringBuilder.toString());
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
