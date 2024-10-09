package ru.liga.loadersystem.validator.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loadersystem.exception.InvalidCargoInput;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@ExtendWith(MockitoExtension.class)
public class CargoFormValidatorTest {

    private final CargoFormValidator validator = new CargoFormValidator();

    @Test
    void testValidate_EmptyForm_ThrowsInvalidCargoInput() {
        char[][] input = new char[][]{};
        assertThatThrownBy(() -> validator.validate(input))
                .isInstanceOf(InvalidCargoInput.class);
    }

    @Test
    void testValidate_FormWithDifferentChars_ThrowsInvalidCargoInput() {
        char[][] input = new char[][]{{'A', 'B'}};
        assertThatThrownBy(() -> validator.validate(input))
                .isInstanceOf(InvalidCargoInput.class);
    }

    @Test
    void testValidate_ValidForm_DoesNotThrow() {
        char[][] input = new char[][]{{'A', 'A'}};
        assertThatCode(() -> validator.validate(input)).doesNotThrowAnyException();
    }

    @Test
    void testValidate_FormWithSpaces_DoesNotThrow() {
        char[][] input = new char[][]{{'A', ' '}, {' ', 'A'}};
        assertThatCode(() -> validator.validate(input)).doesNotThrowAnyException();
    }
}