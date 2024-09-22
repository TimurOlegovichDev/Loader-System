package loadertests.utils;

import loader.model.entites.Cargo;
import loader.utils.CargoCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        cargos.add(new Cargo(new char[][]{{'1'}}));
        cargos.add(new Cargo(new char[][]{{'1'}}));
        cargos.add(new Cargo(new char[][]{{'5', '5'}, {'5', '5', '5'}}));
        cargos.add(new Cargo(new char[][]{{'3', '3', '3'}}));
        cargos.add(new Cargo(new char[][]{{'3', '3', '3'}}));
        cargos.add(new Cargo(new char[][]{{'2', '2'}}));
        cargos.add(new Cargo(new char[][]{{'2', '2'}}));
        cargos.add(new Cargo(new char[][]{{'2', '2'}}));
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
