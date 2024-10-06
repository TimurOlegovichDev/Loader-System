package ru.liga.loader.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.model.entity.Cargo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultCrudCargoRepositoryTest {

    @Mock
    private Map<String, Cargo> cargoData;

    @InjectMocks
    private DefaultCrudCargoRepository repository;

    @Test
    void testGetAll_ReturnsAllCargos() {
        Cargo cargo1 = new Cargo("cargo1", new char[][]{{'A'}});
        Cargo cargo2 = new Cargo("cargo2", new char[][]{{'B'}});
        when(cargoData.values()).thenReturn(List.of(cargo1, cargo2));
        List<Cargo> result = repository.getAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(List.of(cargo1, cargo2).containsAll(result));
    }

    @Test
    void testGetKeys_ReturnsAllKeys() {
        when(cargoData.keySet()).thenReturn(Set.of("cargo1", "cargo2"));
        List<String> result = repository.getKeys();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(Set.of("cargo1", "cargo2").containsAll(result));
    }

    @Test
    void testGetByKey_ReturnsCargoByKey() {
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A'}});
        when(cargoData.get("cargo1")).thenReturn(cargo);
        Cargo result = repository.getBy("cargo1");
        assertNotNull(result);
        assertEquals(cargo, result);
    }

    @Test
    void testUpdate_UpdatesCargo() {
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A'}});
        when(cargoData.put("cargo1", cargo)).thenReturn(cargo);
        Cargo result = repository.update("cargo1", cargo);
        assertNotNull(result);
        assertEquals(cargo, result);
    }

    @Test
    void testDelete_DeletesCargo() {
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A'}});
        when(cargoData.remove("cargo1")).thenReturn(cargo);
        Cargo result = repository.delete("cargo1");
        assertNotNull(result);
        assertEquals(cargo, result);
    }

    @Test
    void testPut_AddsCargo() {
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A'}});
        when(cargoData.put("cargo1", cargo)).thenReturn(cargo);
        Cargo result = repository.put(cargo);
        assertNotNull(result);
        assertEquals(cargo, result);
    }
}