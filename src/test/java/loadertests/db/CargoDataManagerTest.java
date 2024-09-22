package loadertests.db;

import loader.db.CargoDataManager;
import loader.model.entites.Cargo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CargoDataManagerTest {

    @Test
    void testGetData() {
        CargoDataManager manager = new CargoDataManager(new ArrayList<>());
        assertEquals(0, manager.getData().size());

        Cargo cargo1 = new Cargo(new char[][]{{'1'}});
        Cargo cargo2 = new Cargo(new char[][]{{'1'}});

        manager.add(cargo1);
        manager.add(cargo2);

        List<Cargo> data = manager.getData();
        assertEquals(2, data.size());
        assertTrue(data.contains(cargo1));
        assertTrue(data.contains(cargo2));
    }

    @Test
    void testAddSingleCargo() {
        CargoDataManager manager = new CargoDataManager(new ArrayList<>());
        Cargo cargo = new Cargo(new char[][]{{'1'}});

        manager.add(cargo);

        assertEquals(1, manager.getData().size());
        assertTrue(manager.getData().contains(cargo));
    }

    @Test
    void testAddMultipleCargos() {
        CargoDataManager manager = new CargoDataManager(new ArrayList<>());
        Cargo cargo1 = new Cargo(new char[][]{{'A'}});
        Cargo cargo2 = new Cargo(new char[][]{{'B'}});
        Cargo cargo3 = new Cargo(new char[][]{{'C'}});

        List<Cargo> cargos = Arrays.asList(cargo1, cargo2, cargo3);
        manager.add(cargos);

        assertEquals(3, manager.getData().size());
        assertTrue(manager.getData().containsAll(cargos));
    }

    @Test
    void testToString() {
        CargoDataManager manager = new CargoDataManager(new ArrayList<>());
        Cargo cargo1 = new Cargo(new char[][]{{'1'}});
        Cargo cargo2 = new Cargo(new char[][]{{'2', '2'}});

        manager.add(cargo1);
        manager.add(cargo2);

        String expected = cargo1 + "\n" + cargo2 + "\n";
        assertEquals(expected, manager.toString());
    }
}