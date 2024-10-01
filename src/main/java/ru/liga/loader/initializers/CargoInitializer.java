package ru.liga.loader.initializers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loader.factory.cargo.DefaultCargoFactory;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.structure.CargoJsonStructure;
import ru.liga.loader.service.JsonService;
import ru.liga.loader.validator.impl.CargoStructureValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CargoInitializer {

    private final CargoStructureValidator validator;
    private final DefaultCargoFactory defaultCargoFactory;
    private final JsonService jsonService;

    @Autowired
    public CargoInitializer(CargoStructureValidator validator, DefaultCargoFactory defaultCargoFactory, JsonService jsonService) {
        this.validator = validator;
        this.defaultCargoFactory = defaultCargoFactory;
        this.jsonService = jsonService;
    }

    /**
     * Инициализирует грузы по заданным формам.
     * Получает формы грузов из списка и проверяет их на допустимый формат,
     * в случае ошибочного формата, сообщение об ошибке записывается в журнал и груз в список
     * не вносится. В результате мы получаем список грузов правильного формата.
     *
     * @param filepath путь к файлу
     * @return список грузов
     */

    public Map<String, Cargo> initializeFromJson(String filepath) {
        List<CargoJsonStructure> cargoJsonStructures =
                jsonService.read(CargoJsonStructure.class, filepath);
        Map<String, Cargo> map = new HashMap<>();
        for (CargoJsonStructure cargoJsonStructure : cargoJsonStructures) {
            try {
                validator.validate(cargoJsonStructure);
                map.put(
                        cargoJsonStructure.name(),
                        defaultCargoFactory.createCargo(cargoJsonStructure)
                );
            } catch (Exception e) {
                log.error("{} {}", e.getMessage(), cargoJsonStructure.name());
            }
        }
        return map;
    }
}
