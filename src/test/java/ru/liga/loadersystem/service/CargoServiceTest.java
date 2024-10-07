package ru.liga.loadersystem.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loadersystem.factory.cargo.DefaultCargoFactory;
import ru.liga.loadersystem.parser.impl.CargoFormParser;
import ru.liga.loadersystem.repository.CargoCrudRepository;
import ru.liga.loadersystem.validator.impl.CargoFormValidator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CargoServiceTest {

    @Mock
    private CargoFormParser cargoFormParser;

    @Mock
    private CargoFormValidator cargoFormValidator;

    @Mock
    private CargoCrudRepository cargoRepository;

    @Mock
    private DefaultCargoFactory defaultCargoFactory;

    @InjectMocks
    private CargoService cargoService;

    @Test
    void testReplaceNonEmptyCharsWith_ReplacesChars() {
        char[][] originalArray = {
                {'A', 'B'},
                {'C', 'D'}
        };
        char replacementChar = 'X';
        char[][] result = cargoService.replaceNonEmptyCharsWith(originalArray, replacementChar);
        assertNotNull(result);
        assertArrayEquals(
                result,
                new char[][]{
                        {'X', 'X'},
                        {'X', 'X'}
                }
        );
    }
}