package ru.liga.loadersystem.parser.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loadersystem.model.structure.TransportSizeStructure;
import ru.liga.loadersystem.validator.impl.TransportSizeValidator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransportSizeParserTest {

    private final TransportSizeParser parser = new TransportSizeParser(
            new TransportSizeValidator()
    );

    @Test
    void testParse_EmptyString_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""));
    }

    @Test
    void testParse_InvalidFormat_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("invalid"));
    }

    @Test
    void testParse_ValidFormat_ReturnsTransportSizeStructures() {
        List<TransportSizeStructure> result = parser.parse("1x2");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).width());
        assertEquals(2, result.get(0).height());
    }

    @Test
    void testParse_MultipleSizes_ReturnsTransportSizeStructures() {
        List<TransportSizeStructure> result = parser.parse("1x2,3x4");
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(new TransportSizeStructure(1, 2), result.get(0));
        assertEquals(new TransportSizeStructure(3, 4), result.get(1));
    }
}