package ru.liga.loader.utils.initializers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.factory.cargo.DefaultCargoFactory;
import ru.liga.loader.util.initializers.CargoInitializer;
import ru.liga.loader.validator.CargoStructureValidator;

@ExtendWith(MockitoExtension.class)
public class CargoInitializerTest {

    @Mock
    private CargoStructureValidator cargoStructureValidator;

    @Mock
    private DefaultCargoFactory defaultCargoFactory;

    @InjectMocks
    private CargoInitializer cargoInitializer;

}