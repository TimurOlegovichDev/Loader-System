package ru.liga.loader.db;

import org.junit.jupiter.api.Test;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransportDataManagerTest {

    private final TransportDataManager manager = new TransportDataManager(new HashMap<>());

    @Test
    void testGetData() {
        assertEquals(0, manager.getData().size());

        Transport transport1 = new Transport(6, 6);
        Transport transport2 = new Transport(6, 6);

        manager.add(transport1);
        manager.add(transport2);

        List<Transport> data = manager.getData();
        assertEquals(2, data.size());
        assertTrue(data.contains(transport1));
        assertTrue(data.contains(transport2));
    }

    @Test
    void testGetCargos() {
        Transport transport = new Transport(6, 6);
        manager.add(transport);
        assertEquals(0, manager.getCargos(transport).size());
        Cargo cargo1 = new Cargo("", new char[][]{{'A'}});
        Cargo cargo2 = new Cargo("1", new char[][]{{'B'}});
        manager.addCargoInTransport(transport, cargo1);
        manager.addCargoInTransport(transport, cargo2);
        List<Cargo> cargos = manager.getCargos(transport);
        assertEquals(2, cargos.size());
        assertTrue(cargos.contains(cargo1));
        assertTrue(cargos.contains(cargo2));
    }

    @Test
    void testAddTransport() {
        Transport transport = new Transport(6, 6);
        manager.add(transport);
        assertEquals(1, manager.getData().size());
        assertTrue(manager.getData().contains(transport));
    }

    @Test
    void testAddTransports() {
        Transport transport1 = new Transport(6, 6);
        Transport transport2 = new Transport(6, 6);
        Transport transport3 = new Transport(6, 6);
        List<Transport> transports = Arrays.asList(transport1, transport2, transport3);
        manager.add(transports);
        assertEquals(3, manager.getData().size());
        assertTrue(manager.getData().containsAll(transports));
    }

    @Test
    void testAddCargoInTransport() {
        Transport transport = new Transport(6, 6);
        Cargo cargo = new Cargo("", new char[][]{{'A'}});
        manager.add(transport);
        manager.addCargoInTransport(transport, cargo);
        List<Cargo> cargos = manager.getCargos(transport);
        assertEquals(1, cargos.size());
        assertTrue(cargos.contains(cargo));
    }

    @Test
    void testGetCargoWeightInTransport() {
        Transport transport = new Transport(6, 6);
        Cargo cargo1 = new Cargo("", new char[][]{{'1'}});
        Cargo cargo2 = new Cargo("1", new char[][]{{'2', '2'}});
        manager.add(transport);
        manager.addCargoInTransport(transport, cargo1);
        manager.addCargoInTransport(transport, cargo2);
        assertEquals(3, manager.getCargoWeightInTransport(transport));
    }

    @Test
    void testRemove() {
        Transport transport = new Transport(6, 6);
        manager.add(transport);
        assertEquals(1, manager.getData().size());
        manager.remove(transport);
        assertEquals(0, manager.getData().size());
    }

    @Test
    void testToString() {
        Transport transport1 = new Transport(6, 6);
        Transport transport2 = new Transport(6, 6);
        manager.add(transport1);
        manager.add(transport2);
        String expected = transport1 + System.lineSeparator() + transport2 + System.lineSeparator();
        assertEquals(expected, manager.toString());
    }
}