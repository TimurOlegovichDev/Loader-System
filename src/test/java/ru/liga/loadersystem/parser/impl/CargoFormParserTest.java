package ru.liga.loadersystem.parser.impl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CargoFormParserTest {

    private final CargoFormParser parser = new CargoFormParser();

    @Test
    void testParse_EmptyString_ReturnsEmptyArray() {
        char[][] result = parser.parse("");
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    void testParse_SingleLine_ReturnsSingleLineArray() {
        char[][] result = parser.parse("00;000;0000");
        assertThat(result).isNotNull();
        assertThat(result.length).isEqualTo(3);
        assertThat(result[0]).containsExactly('0', '0');
        assertThat(result[1]).containsExactly('0', '0', '0');
        assertThat(result[2]).containsExactly('0', '0', '0', '0');
    }
}