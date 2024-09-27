package ru.liga.loader.db;

import org.junit.jupiter.api.Test;
import ru.liga.loader.model.entity.Cargo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CargoDataManagerTest {

    @Test
    void testGetData() {
        CargoDataManager manager = new CargoDataManager(new HashMap<>());
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
        assertEquals(3, manager.getData().get("FirstType").size());
        assertEquals(3, manager.getData().get("SecondType").size());
        assertEquals(4, manager.getData().get("ThirdType").size());
    }


    @Test
    void test_add_map() {
        CargoDataManager manager = new CargoDataManager(new HashMap<>());
        Map<String, List<Cargo>> map = new HashMap<>();
        List<Cargo> data = new ArrayList<>(
                List.of(
                        new Cargo("FirstType", new char[][]{{'1'}}),
                        new Cargo("SecondType", new char[][]{{'1'}}),
                        new Cargo("ThirdType", new char[][]{{'1'}})
                )
        );
        data.forEach(cargo -> map.put(cargo.getName(), new ArrayList<>(List.of(cargo))));
        manager.add(map);
        for (Map.Entry<String, List<Cargo>> entry : map.entrySet()) {
            assertEquals(entry.getValue().size(), map.get(entry.getKey()).size());
        }
    }

}