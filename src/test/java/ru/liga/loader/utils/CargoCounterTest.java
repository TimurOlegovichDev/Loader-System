package ru.liga.loader.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.util.CargoCounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CargoCounterTest {

    private final CargoCounter counter = new CargoCounter();
    private final List<Cargo> cargos = new ArrayList<>();
    Map<Character, Integer> result = new HashMap<>();

    @BeforeEach
    void setUp() {
        cargos.add(new Cargo("", new char[][]{{'1'}}));
        cargos.add(new Cargo("1", new char[][]{{'1'}}));
        cargos.add(new Cargo("2", new char[][]{{'5', '5'}, {'5', '5', '5'}}));
        cargos.add(new Cargo("3", new char[][]{{'3', '3', '3'}}));
        cargos.add(new Cargo("4", new char[][]{{'3', '3', '3'}}));
        cargos.add(new Cargo("5", new char[][]{{'2', '2'}}));
        cargos.add(new Cargo("6", new char[][]{{'2', '2'}}));
        cargos.add(new Cargo("7", new char[][]{{'2', '2'}}));
        result.put('1', 2);
        result.put('3', 2);
        result.put('5', 1);
        result.put('2', 3);
    }

    @Test
    void test_count_cargos() {
        assertArrayEquals(
                result.entrySet().toArray(),
                counter.countCargos(cargos).entrySet().toArray()
        );
    }
}
