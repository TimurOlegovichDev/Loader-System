package ru.liga.loader.initializers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.factory.cargo.DefaultCargoFactory;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.structure.CargoJsonStructure;
import ru.liga.loader.service.JsonService;
import ru.liga.loader.validator.impl.CargoStructureValidator;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CargoInitializerTest {

    @Mock
    private CargoStructureValidator validator;

    @Mock
    private DefaultCargoFactory defaultCargoFactory;

    @Mock
    private JsonService jsonService;

    @InjectMocks
    private CargoInitializer cargoInitializer;

    @Test
    void testInitializeFromJson_EmptyFile_ReturnsEmptyMap() {
        when(jsonService.read(CargoJsonStructure.class, "filepath")).thenReturn(List.of());
        Map<String, Cargo> cargos = cargoInitializer.initializeFromJson("filepath");
        assertNotNull(cargos);
        assertEquals(0, cargos.size());
    }

    @Test
    void testInitializeFromJson_ReturnsCargoMap() {
        CargoJsonStructure cargoJsonStructure = new CargoJsonStructure("cargo1", new char[][]{{'A'}}, 1, 1, 1, 'A');
        when(jsonService.read(CargoJsonStructure.class, "filepath")).thenReturn(List.of(cargoJsonStructure));
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A'}});
        when(defaultCargoFactory.createCargo(cargoJsonStructure)).thenReturn(cargo);
        Map<String, Cargo> cargos = cargoInitializer.initializeFromJson("filepath");
        assertNotNull(cargos);
        assertEquals(1, cargos.size());
        assertEquals(cargo, cargos.get("cargo1"));
    }


    @Test
    void testInitializeFromJson_MultipleCargos_ReturnsCargoMap() {
        CargoJsonStructure cargoJsonStructure1 = new CargoJsonStructure("cargo1", new char[][]{{'A'}}, 1, 1, 1, 'A');
        CargoJsonStructure cargoJsonStructure2 = new CargoJsonStructure("cargo2", new char[][]{{'B'}}, 1, 1, 1, 'B');
        when(jsonService.read(CargoJsonStructure.class, "filepath")).thenReturn(List.of(cargoJsonStructure1, cargoJsonStructure2));
        Cargo cargo1 = new Cargo("cargo1", new char[][]{{'A'}});
        Cargo cargo2 = new Cargo("cargo2", new char[][]{{'B'}});
        when(defaultCargoFactory.createCargo(cargoJsonStructure1)).thenReturn(cargo1);
        when(defaultCargoFactory.createCargo(cargoJsonStructure2)).thenReturn(cargo2);
        Map<String, Cargo> cargos = cargoInitializer.initializeFromJson("filepath");
        assertNotNull(cargos);
        assertEquals(2, cargos.size());
        assertEquals(cargo1, cargos.get("cargo1"));
        assertEquals(cargo2, cargos.get("cargo2"));
    }
}