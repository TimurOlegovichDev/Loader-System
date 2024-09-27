package ru.liga.loader.validator;

import org.junit.jupiter.api.Test;
import ru.liga.loader.exception.InvalidCargoInput;
import ru.liga.loader.model.structure.CargoJsonStructure;

import static org.junit.jupiter.api.Assertions.*;

public class CargoValidatorTest {

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
        CargoValidator validator = new CargoValidator();
        assertDoesNotThrow(
                () -> validator.validate(cargo)
        );
        assertDoesNotThrow(
                () -> validator.validate(cargo2)
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
        CargoValidator validator = new CargoValidator();
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
        CargoValidator validator = new CargoValidator();
        InvalidCargoInput exception = assertThrows(InvalidCargoInput.class, () -> validator.validate(cargo));
        assertEquals("Ожидаемыемые значения отличаются от исходных!" + System.lineSeparator() +
                "Ожидаемый размер груза: 10" + System.lineSeparator() +
                "Фактический размер груза: 9" + System.lineSeparator() +
                "Проверяемый груз: Invalid Area", exception.getMessage());
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
        CargoValidator validator = new CargoValidator();
        InvalidCargoInput exception = assertThrows(InvalidCargoInput.class, () -> validator.validate(cargo));
        assertEquals("Груз поврежден, имеется символ другого типа: B" + System.lineSeparator() +
                "Проверяемый груз: Invalid Form", exception.getMessage());
    }
}