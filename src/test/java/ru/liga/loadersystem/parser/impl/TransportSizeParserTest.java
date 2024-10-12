package ru.liga.loadersystem.parser.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loadersystem.model.dto.TransportDto;
import ru.liga.loadersystem.validator.impl.TransportSizeValidator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class TransportSizeParserTest {

    private final TransportSizeParser parser = new TransportSizeParser(
            new TransportSizeValidator()
    );

    @Test
    void testParse_EmptyString_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> parser.parse(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testParse_InvalidFormat_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> parser.parse("invalid"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testParse_ValidFormat_ReturnsTransportSizeStructures() {
        List<TransportDto> result = parser.parse("1x2");
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(new TransportDto(1, 2));
    }

    @Test
    void testParse_MultipleSizes_ReturnsTransportSizeStructures() {
        List<TransportDto> result = parser.parse("1x2,3x4");
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(
                new TransportDto(1, 2),
                new TransportDto(3, 4)
        );
    }
}