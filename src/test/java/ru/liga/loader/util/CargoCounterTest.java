package ru.liga.loader.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.model.entity.Cargo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CargoCounterTest {

    private final CargoCounter cargoCounter = new CargoCounter();

    @Test
    void testCount_CountsCargos() {
        List<Cargo> cargos = new ArrayList<>();
        cargos.add(new Cargo("cargo1", new char[][]{{'A'}}));
        cargos.add(new Cargo("cargo1", new char[][]{{'A'}}));
        cargos.add(new Cargo("cargo2", new char[][]{{'B'}}));
        Map<String, Integer> result = cargoCounter.count(cargos);
        assertNotNull(result);
        assertEquals(2, result.get("cargo1"));
        assertEquals(1, result.get("cargo2"));
    }

    @Test
    void testCount_EmptyList_ReturnsEmptyMap() {
        List<Cargo> cargos = new ArrayList<>();
        Map<String, Integer> result = cargoCounter.count(cargos);
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}