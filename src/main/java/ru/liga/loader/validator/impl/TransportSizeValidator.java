package ru.liga.loader.validator.impl;

import org.springframework.stereotype.Component;
import ru.liga.loader.validator.Validator;

@Component
public class TransportSizeValidator implements Validator<String> {

    private final String NUMBER_PATTERN = "\\d+x\\d+";

    public void validate(String input) {
        String[] parts = input.split(",");
        for (String part : parts) {
            if (!part.matches(NUMBER_PATTERN)) {
                throw new IllegalArgumentException("Неверный формат введенных данных: " + part);
            }
        }
    }
}
