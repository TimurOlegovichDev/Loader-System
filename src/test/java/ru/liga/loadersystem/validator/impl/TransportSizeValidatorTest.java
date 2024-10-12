package ru.liga.loadersystem.validator.impl;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TransportSizeValidatorTest {

    private final TransportSizeValidator validator = new TransportSizeValidator();

    @Test
    void test_valid_input_line() {
        List<String> input = List.of(
                "3x3,1x2,5x5,6x6,8x8",
                "3x3,1231x2131",
                "3x3"
        );
        for (String line : input) {
            assertThatCode(() -> validator.validate(line)).doesNotThrowAnyException();
        }
    }

    @Test
    void test_invalid_input_line() {
        List<String> input = List.of(
                "3x3,1x2,5x5,6x6g,8x8",
                "3x3,1x2,5x5,6*6,8x8",
                "3x3,1xa,5x5,6x6,8x8",
                "3x3.1x2,5x5,6x6,8x8",
                "3x3,1x2,5x5,6x6,8x",
                "3x3,12,5x5,6x6,8x8",
                "3x3 1x2 5x5 6x6 8x8"
        );
        for (String line : input) {
            assertThatThrownBy(() -> validator.validate(line))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}