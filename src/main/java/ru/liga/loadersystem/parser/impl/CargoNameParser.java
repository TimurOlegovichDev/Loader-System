package ru.liga.loadersystem.parser.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.parser.StringParser;
import ru.liga.loadersystem.repository.CargoCrudRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CargoNameParser implements StringParser<List<Cargo>> {

    private final CargoCrudRepository repository;

    /**
     * Разбирает строку на коллекцию объектов Cargo.
     *
     * @param input строка, содержащая имена грузов, разделенные запятыми
     * @return коллекция объектов Cargo, соответствующих грузам из входной строки
     */
    @Override
    public List<Cargo> parse(String input) {
        List<Cargo> cargos = new ArrayList<>();
        for (String name : input.split(",")) {
            if (repository.findByName(name) != null) {
                cargos.add(repository.findByName(name));
            } else {
                log.warn("Такое название не найдено: {}", name);
            }
        }
        return cargos;
    }
}
