package loadertests.db;

import loader.db.TransportDataManager;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransportDataManagerTest {

    @Test
    void testGetData() {
        TransportDataManager manager = new TransportDataManager();
        assertEquals(0, manager.getData().size());

        Transport transport1 = new Transport();
        Transport transport2 = new Transport();

        manager.add(transport1);
        manager.add(transport2);

        List<Transport> data = manager.getData();
        assertEquals(2, data.size());
        assertTrue(data.contains(transport1));
        assertTrue(data.contains(transport2));
    }

    @Test
    void testGetCargos() {
        TransportDataManager manager = new TransportDataManager();
        Transport transport = new Transport();

        manager.add(transport);

        assertEquals(0, manager.getCargos(transport).size());

        Cargo cargo1 = new Cargo(new char[][]{{'A'}});
        Cargo cargo2 = new Cargo(new char[][]{{'B'}});

        manager.addCargoInTransport(transport, cargo1);
        manager.addCargoInTransport(transport, cargo2);

        List<Cargo> cargos = manager.getCargos(transport);
        assertEquals(2, cargos.size());
        assertTrue(cargos.contains(cargo1));
        assertTrue(cargos.contains(cargo2));
    }

    @Test
    void testAddTransport() {
        TransportDataManager manager = new TransportDataManager();
        Transport transport = new Transport();

        manager.add(transport);

        assertEquals(1, manager.getData().size());
        assertTrue(manager.getData().contains(transport));
    }

    @Test
    void testAddTransports() {
        TransportDataManager manager = new TransportDataManager();
        Transport transport1 = new Transport();
        Transport transport2 = new Transport();
        Transport transport3 = new Transport();

        List<Transport> transports = Arrays.asList(transport1, transport2, transport3);
        manager.add(transports);

        assertEquals(3, manager.getData().size());
        assertTrue(manager.getData().containsAll(transports));
    }

    @Test
    void testAddCargoInTransport() {
        TransportDataManager manager = new TransportDataManager();
        Transport transport = new Transport();
        Cargo cargo = new Cargo(new char[][]{{'A'}});

        manager.add(transport);
        manager.addCargoInTransport(transport, cargo);

        List<Cargo> cargos = manager.getCargos(transport);
        assertEquals(1, cargos.size());
        assertTrue(cargos.contains(cargo));
    }

    @Test
    void testGetCargoWeightInTransport() {
        TransportDataManager manager = new TransportDataManager();
        Transport transport = new Transport();
        Cargo cargo1 = new Cargo(new char[][]{{'1'}}); // weight = 1
        Cargo cargo2 = new Cargo(new char[][]{{'2', '2'}}); // weight = 4

        manager.add(transport);
        manager.addCargoInTransport(transport, cargo1);
        manager.addCargoInTransport(transport, cargo2);

        assertEquals(5, manager.getCargoWeightInTransport(transport));
    }

    @Test
    void testRemove() {
        TransportDataManager manager = new TransportDataManager();
        Transport transport = new Transport();

        manager.add(transport);

        assertEquals(1, manager.getData().size());

        manager.remove(transport);

        assertEquals(0, manager.getData().size());
    }

    @Test
    void testToString() {
        TransportDataManager manager = new TransportDataManager();
        Transport transport1 = new Transport();
        Transport transport2 = new Transport();

        manager.add(transport1);
        manager.add(transport2);

        String expected = transport1 + System.lineSeparator() + transport2 + System.lineSeparator();
        assertEquals(expected, manager.toString());
    }
}