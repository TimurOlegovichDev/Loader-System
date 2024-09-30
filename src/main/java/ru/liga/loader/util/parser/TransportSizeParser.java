package ru.liga.loader.util.parser;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loader.model.structure.TransportSizeStructure;
import ru.liga.loader.validator.TransportSizeValidator;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransportSizeParser {

    private final TransportSizeValidator validator;

    @Autowired
    public TransportSizeParser(TransportSizeValidator validator) {
        this.validator = validator;
    }

    public List<TransportSizeStructure> parse(@NonNull String input) {
        validator.validate(input.trim());
        List<TransportSizeStructure> pairs = new ArrayList<>();
        String[] parts = input.split(",");
        for (String part : parts) {
            String[] numbers = part.split("x");
            pairs.add(new TransportSizeStructure(
                    Integer.parseInt(numbers[0]),
                    Integer.parseInt(numbers[1])
            ));
        }
        return pairs;
    }
}