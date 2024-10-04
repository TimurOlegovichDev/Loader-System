package ru.liga.loader.parser.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.repository.CargoCrudRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CargoNameParserTest {

    @Mock
    private CargoCrudRepository repository;

    @InjectMocks
    private CargoNameParser parser;

    @Test
    void testParse_EmptyString_ReturnsEmptyList() {
        List<Cargo> result = parser.parse("");
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testParse_SingleCargo_ReturnsSingleCargoList() {
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A'}});
        when(repository.getKeys()).thenReturn(List.of("cargo1"));
        when(repository.getBy("cargo1")).thenReturn(cargo);
        List<Cargo> result = parser.parse("cargo1");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cargo, result.get(0));
    }

    @Test
    void testParse_MultipleCargos_ReturnsMultipleCargoList() {
        Cargo cargo1 = new Cargo("cargo1", new char[][]{{'A'}});
        Cargo cargo2 = new Cargo("cargo2", new char[][]{{'B'}});
        when(repository.getKeys()).thenReturn(List.of("cargo1", "cargo2"));
        when(repository.getBy("cargo1")).thenReturn(cargo1);
        when(repository.getBy("cargo2")).thenReturn(cargo2);
        List<Cargo> result = parser.parse("cargo1,cargo2");
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(cargo1, result.get(0));
        assertEquals(cargo2, result.get(1));
    }

    @Test
    void testParse_UnknownCargo_LogsWarning() {
        when(repository.getKeys()).thenReturn(List.of("cargo1"));
        List<Cargo> result = parser.parse("cargo2");
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}