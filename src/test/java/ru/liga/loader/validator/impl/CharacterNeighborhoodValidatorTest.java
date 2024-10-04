package ru.liga.loader.validator.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.exception.InvalidCargoInput;
import ru.liga.loader.model.structure.CargoJsonStructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CharacterNeighborhoodValidatorTest {

    private final CharacterNeighborhoodValidator validator = new CharacterNeighborhoodValidator();

    @Test
    void testValidate_FormWithSingleElement_DoesNotThrow() {
        CargoJsonStructure cargo = new CargoJsonStructure("cargo1", new char[][]{{'A'}}, 0, 0, 0, '0');
        validator.validate(cargo);
        assertDoesNotThrow(() -> validator.validate(cargo));
    }

    @Test
    void testValidate_FormWithNeighbors_DoesNotThrow() {
        CargoJsonStructure cargo = new CargoJsonStructure("cargo1", new char[][]{
                {'A', 'A'},
                {'A', 'A'}}, 0, 0, 0, '0');
        assertDoesNotThrow(() -> validator.validate(cargo));
    }

    @Test
    void testValidate_FormWithoutNeighbors_ThrowsInvalidCargoInput() {
        CargoJsonStructure cargo = new CargoJsonStructure("cargo1", new char[][]{
                {'A', ' '},
                {' ', 'A'}}, 0, 0, 0, '0');
        assertThrows(InvalidCargoInput.class, () -> validator.validate(cargo));
    }

    @Test
    void testValidate_FormWithMultipleElementsWithoutNeighbors_ThrowsInvalidCargoInput() {
        CargoJsonStructure cargo = new CargoJsonStructure("cargo1", new char[][]{
                {'A', ' '},
                {' ', 'B'}}, 0, 0, 0, '0');
        assertThrows(InvalidCargoInput.class, () -> validator.validate(cargo));
    }
}