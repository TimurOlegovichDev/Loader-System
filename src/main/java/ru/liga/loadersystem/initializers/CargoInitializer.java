package ru.liga.loadersystem.initializers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loadersystem.factory.cargo.DefaultCargoFactory;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.structure.CargoJsonStructure;
import ru.liga.loadersystem.service.JsonService;
import ru.liga.loadersystem.validator.impl.CargoStructureValidator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
     * Считывает грузы и инициализирует их в памяти из json файла.
     *
     * @param stream файл
     * @return список грузов
     */

    public List<Cargo> initializeFromJson(InputStream stream) throws Exception {
        List<CargoJsonStructure> cargoJsonStructures =
                jsonService.read(CargoJsonStructure.class, stream);
        List<Cargo> list = new ArrayList<>();
        for (CargoJsonStructure cargoJsonStructure : cargoJsonStructures) {
            try {
                validator.validate(cargoJsonStructure);
                list.add(defaultCargoFactory.createCargo(cargoJsonStructure));

            } catch (Exception e) {
                log.error("{} {}", e.getMessage(), cargoJsonStructure.name());
            }
        }
        return list;
    }
}
