package ru.liga.loadersystem.validator.impl;

import org.springframework.stereotype.Component;
import ru.liga.loadersystem.validator.Validator;

@Component
public class TransportSizeValidator implements Validator<String> {

    private final String NUMBER_PATTERN = "\\d+x\\d+";

    /**
     * Проверяет входную строку на формат NxM, при этом числа должны быть неотрицательными
     *
     * @param input - строка для проверки
     */
    public void validate(String input) {
        String[] parts = input.split(",");
        for (String part : parts) {
            if (!part.matches(NUMBER_PATTERN)) {
                throw new IllegalArgumentException("Неверный формат введенных данных: " + part);
            }
        }
    }
}
