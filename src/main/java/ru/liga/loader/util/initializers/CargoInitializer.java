package ru.liga.loader.util.initializers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.factory.cargo.DefaultCargoFactory;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.structure.CargoJsonStructure;
import ru.liga.loader.util.json.JsonService;
import ru.liga.loader.validator.CargoValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CargoInitializer {

    private final CargoValidator validator;
    private final DefaultCargoFactory defaultCargoFactory;
    private final JsonService jsonService;

    /**
     * Инициализирует грузы по заданным формам.
     * Получает формы грузов из списка и проверяет их на допустимый формат,
     * в случае ошибочного формата, сообщение об ошибке записывается в журнал и груз в список
     * не вносится. В результате мы получаем список грузов правильного формата.
     *
     * @param filepath путь к файлу
     * @return список грузов
     */

    public Map<String, List<Cargo>> initializeFromJson(String filepath) {
        List<CargoJsonStructure> cargoJsonStructures =
                jsonService.read(CargoJsonStructure.class, filepath);
        Map<String, List<Cargo>> map = new HashMap<>();
        for (CargoJsonStructure cargoJsonStructure : cargoJsonStructures) {
            try {
                validator.validate(cargoJsonStructure);
                if (!map.containsKey(cargoJsonStructure.getName())) {
                    map.put(cargoJsonStructure.getName(), new ArrayList<>());
                }
                map.get(cargoJsonStructure.getName()).add(new Cargo(cargoJsonStructure));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return map;
    }
}
