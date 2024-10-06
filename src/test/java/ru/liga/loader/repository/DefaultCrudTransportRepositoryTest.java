package ru.liga.loader.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultCrudTransportRepositoryTest {

    @Mock
    private Map<Transport, List<Cargo>> transportMap;

    @InjectMocks
    private DefaultCrudTransportRepository repository;

    @Test
    void testGetAll_ReturnsAllCargos() {
        Cargo cargo1 = new Cargo("cargo1", new char[][]{{'A'}});
        Cargo cargo2 = new Cargo("cargo2", new char[][]{{'B'}});
        when(transportMap.values()).thenReturn(List.of(List.of(cargo1, cargo2)));
        List<List<Cargo>> result = repository.getAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).size());
        assertEquals(cargo1, result.get(0).get(0));
        assertEquals(cargo2, result.get(0).get(1));
    }

    @Test
    void testGetKeys_ReturnsAllTransports() {
        Transport transport1 = new Transport(1, 1);
        Transport transport2 = new Transport(1, 1);
        when(transportMap.keySet()).thenReturn(Set.of(transport1, transport2));
        List<Transport> result = repository.getKeys();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(Set.of(transport1, transport2).containsAll(result));
    }

    @Test
    void testGetBy_ReturnsCargosByTransport() {
        Cargo cargo1 = new Cargo("cargo1", new char[][]{{'A'}});
        Cargo cargo2 = new Cargo("cargo2", new char[][]{{'B'}});
        Transport transport = new Transport(1, 1);
        when(transportMap.get(transport)).thenReturn(List.of(cargo1, cargo2));
        List<Cargo> result = repository.getBy(transport);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(List.of(cargo1, cargo2).containsAll(result));
    }

    @Test
    void testAddAll_AddsTransportsWithCargos() {
        Map<Transport, List<Cargo>> entities = new HashMap<>();
        entities.put(new Transport(1, 1), List.of(new Cargo("cargo1", new char[][]{{'A'}})));
        entities.put(new Transport(1, 1), List.of(new Cargo("cargo2", new char[][]{{'B'}})));
        when(transportMap.keySet()).thenReturn(entities.keySet());
        repository.addAll(entities);
        assertEquals(entities.size(), repository.getKeys().size());
    }

    @Test
    void testUpdate_UpdatesCargosForTransport() {
        Cargo cargo1 = new Cargo("cargo1", new char[][]{{'A'}});
        Cargo cargo2 = new Cargo("cargo2", new char[][]{{'B'}});
        Transport transport = new Transport(1, 1);
        when(transportMap.put(transport, List.of(cargo1, cargo2))).thenReturn(List.of(cargo1, cargo2));
        List<Cargo> result = repository.update(transport, List.of(cargo1, cargo2));
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(cargo1, result.get(0));
        assertEquals(cargo2, result.get(1));
    }

    @Test
    void testDelete_DeletesTransport() {
        Cargo cargo1 = new Cargo("cargo1", new char[][]{{'A'}});
        Cargo cargo2 = new Cargo("cargo2", new char[][]{{'B'}});
        Transport transport = new Transport(1, 1);
        when(transportMap.remove(transport)).thenReturn(List.of(cargo1, cargo2));
        List<Cargo> result = repository.delete(transport);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(cargo1, result.get(0));
        assertEquals(cargo2, result.get(1));
    }

    @Test
    void testGetCargos_ReturnsCargosForTransport() {
        Cargo cargo1 = new Cargo("cargo1", new char[][]{{'A'}});
        Cargo cargo2 = new Cargo("cargo2", new char[][]{{'B'}});
        Transport transport = new Transport(1, 1);
        when(transportMap.get(transport)).thenReturn(List.of(cargo1, cargo2));
        List<Cargo> result = repository.getCargos(transport);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(cargo1, result.get(0));
        assertEquals(cargo2, result.get(1));
    }

    @Test
    void testGetAllCargos_ReturnsAllCargos() {
        Cargo cargo1 = new Cargo("cargo1", new char[][]{{'A'}});
        Cargo cargo2 = new Cargo("cargo2", new char[][]{{'B'}});
        Transport transport1 = new Transport("id", new char[][]{{'A'}});
        Transport transport2 = new Transport("id2", new char[][]{{'A'}});
        when(transportMap.keySet()).thenReturn(Set.of(transport1, transport2));
        when(transportMap.get(transport1)).thenReturn(List.of(cargo1));
        when(transportMap.get(transport2)).thenReturn(List.of(cargo2));
        List<Cargo> result = repository.getAllCargos();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(List.of(cargo1, cargo2).containsAll(result));
    }
}