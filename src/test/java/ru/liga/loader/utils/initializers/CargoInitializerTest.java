package ru.liga.loader.utils.initializers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.factory.cargo.DefaultCargoFactory;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.util.initializers.CargoInitializer;
import ru.liga.loader.validator.CargoValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CargoInitializerTest {

    @Mock
    private CargoValidator cargoValidator;

    @Mock
    private DefaultCargoFactory defaultCargoFactory;

    @InjectMocks
    private CargoInitializer cargoInitializer;

    @Test
    void testInitialize_withValidForms_returnsInitializedCargos() {
        List<String> forms = Arrays.asList(
                "1",
                "",
                "22",
                "",
                "333"
        );
        List<Cargo> expectedCargos = new ArrayList<>(List.of(
                new Cargo(
                        new char[][]{{'1'}}
                ),
                new Cargo(
                        new char[][]{{'2', '2'}}
                ),
                new Cargo(
                        new char[][]{{'3', '3', '3'}}
                ))
        );
        when(defaultCargoFactory.createCargo(any())).thenReturn(any());
        List<Cargo> actualCargos = cargoInitializer.initialize(forms);
        assertEquals(
                actualCargos.size(),
                expectedCargos.size()
        );
    }

    @Test
    void testInitialize_withValidForm_returnsEmptyList() {
        List<String> forms = Arrays.asList(
                "1",
                "",
                "22",
                "",
                "333"
        );
        doThrow(new RuntimeException("invalid box")).when(cargoValidator).validate(anyList());
        List<Cargo> actualList = cargoInitializer.initialize(forms);
        assertTrue(
                actualList.isEmpty()
        );
    }
}