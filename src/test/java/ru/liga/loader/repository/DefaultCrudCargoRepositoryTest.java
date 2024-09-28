package ru.liga.loader.repository;

import org.junit.jupiter.api.Test;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.repository.impl.DefaultCrudCargoRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class DefaultCrudCargoRepositoryTest {

    @Test
    void testGetData() {
        DefaultCrudCargoRepository manager = new DefaultCrudCargoRepository(new HashMap<>());
        assertEquals(0, manager.getAll().size());

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
        assertEquals(3, manager.getAll().size());
    }


    @Test
    void test_add_map() {
        DefaultCrudCargoRepository manager = new DefaultCrudCargoRepository(new HashMap<>());
        Map<String, Cargo> map = new HashMap<>();
        List<Cargo> data = new ArrayList<>(
                List.of(
                        new Cargo("FirstType", new char[][]{{'1'}}),
                        new Cargo("SecondType", new char[][]{{'1'}}),
                        new Cargo("ThirdType", new char[][]{{'1'}})
                )
        );
        for (Cargo cargo : data) {
            map.put(cargo.getName(), cargo);
        }
        manager.addAll(map);
        for (Cargo cargo : data) {
            assertArrayEquals(
                    cargo.getForm(),
                    map.get(cargo.getName()).getForm()
            );
        }
    }

}