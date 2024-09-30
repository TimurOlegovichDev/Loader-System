package ru.liga.loader.util;

import org.junit.jupiter.api.Test;
import ru.liga.loader.util.parser.TransportSizeParser;
import ru.liga.loader.validator.TransportSizeValidator;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransportSizeParserTest {

    private final TransportSizeParser parser = new TransportSizeParser(new TransportSizeValidator());

    private final Map<String, Integer> input = Map.of(
            "3x3,1x2,5x5,6x6,8x8,3x3,1x2,5x5,6x6,8x8", 10,
            "3x3,1231x2131", 2,
            "3x3", 1
    );

    @Test
    void testParse() {
        for (Map.Entry<String, Integer> entry : input.entrySet()) {
            assertEquals(
                    parser.parse(entry.getKey()).size(),
                    entry.getValue()
            );
        }
    }
}