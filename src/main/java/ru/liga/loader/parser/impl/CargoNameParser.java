package ru.liga.loader.parser.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.parser.StringParser;
import ru.liga.loader.repository.CargoCrudRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CargoNameParser implements StringParser<List<Cargo>> {

    private final CargoCrudRepository repository;

    @Autowired
    public CargoNameParser(CargoCrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cargo> parse(String input) {
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
