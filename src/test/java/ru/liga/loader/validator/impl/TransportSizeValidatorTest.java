package ru.liga.loader.validator.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
            Assertions.assertDoesNotThrow(
                    () -> validator.validate(line)
            );
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
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> validator.validate(line)
            );
        }
    }
}