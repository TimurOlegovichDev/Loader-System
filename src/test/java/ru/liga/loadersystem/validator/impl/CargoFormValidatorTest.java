package ru.liga.loadersystem.validator.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loadersystem.exception.InvalidCargoInput;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CargoFormValidatorTest {

    private final CargoFormValidator validator = new CargoFormValidator();

    @Test
    void testValidate_EmptyForm_ThrowsInvalidCargoInput() {
        char[][] input = new char[][]{};
        assertThrows(InvalidCargoInput.class, () -> validator.validate(input));
    }

    @Test
    void testValidate_FormWithDifferentChars_ThrowsInvalidCargoInput() {
        char[][] input = new char[][]{{'A', 'B'}};
        assertThrows(InvalidCargoInput.class, () -> validator.validate(input));
    }

    @Test
    void testValidate_ValidForm_DoesNotThrow() {
        char[][] input = new char[][]{{'A', 'A'}};
        assertDoesNotThrow(() -> validator.validate(input));
    }

    @Test
    void testValidate_FormWithSpaces_DoesNotThrow() {
        char[][] input = new char[][]{{'A', ' '}, {' ', 'A'}};
        assertDoesNotThrow(() -> validator.validate(input));
    }
}