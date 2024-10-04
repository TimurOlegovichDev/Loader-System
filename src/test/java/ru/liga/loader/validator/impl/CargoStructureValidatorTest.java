package ru.liga.loader.validator.impl;

import org.junit.jupiter.api.Test;
import ru.liga.loader.exception.InvalidCargoInput;
import ru.liga.loader.model.structure.CargoJsonStructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CargoStructureValidatorTest {

    private final CargoStructureValidator validator = new CargoStructureValidator(
            new CharacterNeighborhoodValidator()
    );

    @Test
    void testValidCargo() throws InvalidCargoInput {
        CargoJsonStructure cargo = new CargoJsonStructure(
                "Valid Cargo",
                new char[][]{
                        {'A', 'A', 'A'},
                        {'A', ' ', 'A'},
                        {'A', 'A', 'A'}
                },
                3,
                3,
                9,
                'A'
        );
        CargoJsonStructure cargo2 = new CargoJsonStructure(
                "Valid Cargo",
                new char[][]{
                        {'A'},
                },
                1,
                1,
                1,
                'A'
        );
        CargoJsonStructure cargo3 = new CargoJsonStructure(
                "Valid Cargo",
                new char[][]{
                        {'0'},
                        {'0', '0'},
                        {'0', '0', '0'},
                        {'0', '0', '0'},
                        {'0', '0', '0'},
                        {'0', '0', '0', '0'},
                        {'0', '0', '0', '0'},
                        {'0', '0', '0', '0'},
                        {'0', '0', '0', '0'},
                },
                9,
                4,
                36,
                '0'
        );
        assertDoesNotThrow(
                () -> validator.validate(cargo)
        );
        assertDoesNotThrow(
                () -> validator.validate(cargo2)
        );
        assertDoesNotThrow(
                () -> validator.validate(cargo3)
        );
    }

    @Test
    void testDiagonalCargo() throws InvalidCargoInput {
        CargoJsonStructure cargo = new CargoJsonStructure(
                "Valid Cargo",
                new char[][]{
                        {'A', ' ', 'A'},
                        {' ', 'A', ' '},
                        {'A', ' ', 'A'}
                },
                3,
                3,
                9,
                'A'
        );
        assertThrows(InvalidCargoInput.class,
                () -> validator.validate(cargo)
        );
    }

    @Test
    void testInvalidArea() {
        CargoJsonStructure cargo = new CargoJsonStructure(
                "Invalid Area",
                new char[][]{
                        {'A', 'A', 'A'},
                        {'A', ' ', 'A'},
                        {'A', 'A', 'A'}
                },
                3,
                3,
                10,
                'A'
        );
        assertThrows(InvalidCargoInput.class, () -> validator.validate(cargo));
    }

    @Test
    void testInvalidForm() {
        CargoJsonStructure cargo = new CargoJsonStructure(
                "Invalid Form",
                new char[][]{
                        {'A', 'B', 'A'},
                        {'A', ' ', 'A'},
                        {'A', 'A', 'A'}
                },
                3,
                3,
                9,
                'A'
        );
        assertThrows(InvalidCargoInput.class, () -> validator.validate(cargo));
    }
}