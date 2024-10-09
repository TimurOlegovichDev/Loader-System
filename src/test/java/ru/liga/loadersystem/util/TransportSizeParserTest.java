package ru.liga.loadersystem.util;

import org.junit.jupiter.api.Test;
import ru.liga.loadersystem.parser.impl.TransportSizeParser;
import ru.liga.loadersystem.validator.impl.TransportSizeValidator;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
            assertThat(parser.parse(entry.getKey()).size())
                    .isEqualTo(entry.getValue());
        }
    }
}