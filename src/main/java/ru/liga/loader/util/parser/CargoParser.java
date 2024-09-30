package ru.liga.loader.util.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.repository.CargoCrudRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CargoParser {

    public List<Cargo> parse(String input, CargoCrudRepository repository) {
        List<Cargo> cargos = new ArrayList<>();
        for (String name : input.split(",")) {
            if (repository.getKeys().contains(name)) {
                cargos.add(repository.getBy(name));
            } else {
                log.warn("Такое название не найдено: {}", name);
            }
        }
        return cargos;
    }
}
