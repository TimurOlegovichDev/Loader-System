package ru.liga.loadersystem.parser.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loadersystem.model.dto.TransportDto;
import ru.liga.loadersystem.parser.StringParser;
import ru.liga.loadersystem.validator.impl.TransportSizeValidator;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TransportSizeParser implements StringParser<List<TransportDto>> {

    private final TransportSizeValidator validator;

    /**
     * Разбирает строку на коллекцию объектов TransportSizeDto.
     *
     * @param input строка, содержащая размеры транспорта в формате "NxM"
     * @return коллекция объектов TransportDto
     * @throws IllegalArgumentException если входная строка не проходит валидацию
     */
    @Override
    public List<TransportDto> parse(String input) {
        validator.validate(input.trim());
        List<TransportDto> pairs = new ArrayList<>();
        if (input.isEmpty()) {
            return pairs;
        }
        String[] parts = input.split(",");
        for (String part : parts) {
            String[] numbers = part.split("x");
            pairs.add(new TransportDto(
                    Integer.parseInt(numbers[0]),
                    Integer.parseInt(numbers[1])
            ));
        }
        return pairs;
    }
}