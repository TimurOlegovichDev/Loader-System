package ru.liga.loader.repository;

import org.junit.jupiter.api.Test;
import ru.liga.loader.model.entity.Cargo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CargoDataRepositoryTest {

    @Test
    void testGetData() {
        CargoDataRepository manager = new CargoDataRepository(new HashMap<>());
        assertEquals(0, manager.getData().size());

        List<Cargo> data = new ArrayList<>(
                List.of(
                        new Cargo("FirstType", new char[][]{{'1'}}),
                        new Cargo("FirstType", new char[][]{{'1'}}),
                        new Cargo("FirstType", new char[][]{{'1'}}),
                        new Cargo("SecondType", new char[][]{{'1'}}),
                        new Cargo("SecondType", new char[][]{{'1'}}),
                        new Cargo("SecondType", new char[][]{{'1'}}),
                        new Cargo("ThirdType", new char[][]{{'1'}}),
                        new Cargo("ThirdType", new char[][]{{'1'}}),
                        new Cargo("ThirdType", new char[][]{{'1'}}),
                        new Cargo("ThirdType", new char[][]{{'1'}})
                )
        );

        for (Cargo cargo : data) {
            manager.add(cargo);
        }
        assertEquals(3, manager.getData().size());
        assertEquals(3, manager.getData().size());
    }


    @Test
    void test_add_map() {
        CargoDataRepository manager = new CargoDataRepository(new HashMap<>());
        Map<String, Cargo> map = new HashMap<>();
        List<Cargo> data = new ArrayList<>(
                List.of(
                        new Cargo("FirstType", new char[][]{{'1'}}),
                        new Cargo("SecondType", new char[][]{{'1'}}),
                        new Cargo("ThirdType", new char[][]{{'1'}})
                )
        );
        manager.add(map);
        for (Cargo cargo : data) {
            assertArrayEquals(cargo.getForm(), map.get(cargo.getName()).getForm());
        }
    }

}