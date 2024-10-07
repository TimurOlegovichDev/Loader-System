package ru.liga.loadersystem.parser.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CargoFormParserTest {

    private final CargoFormParser parser = new CargoFormParser();

    @Test
    void testParse_EmptyString_ReturnsEmptyArray() {
        char[][] result = parser.parse("");
        assertNotNull(result);
        assertEquals(0, result.length);
    }

    @Test
    void testParse_SingleLine_ReturnsSingleLineArray() {
        char[][] result = parser.parse("00 000 0000");
        assertNotNull(result);
        assertEquals(3, result.length);
        assertArrayEquals(new char[]{'0', '0'}, result[0]);
        assertArrayEquals(new char[]{'0', '0', '0'}, result[1]);
        assertArrayEquals(new char[]{'0', '0', '0', '0'}, result[2]);
    }
}