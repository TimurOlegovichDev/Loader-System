package ru.liga.loadersystem.validator.impl;

import org.springframework.stereotype.Component;
import ru.liga.loadersystem.exception.InvalidCargoInput;
import ru.liga.loadersystem.validator.Validator;

@Component
public class CargoFormValidator implements Validator<char[][]> {

    /**
     * Проверяет форму груза, ориентируясь на первый введенный символ.
     *
     * @param input - форма груза для проверки
     */

    @Override
    public void validate(char[][] input) {
        if (input.length == 0 || input[0].length == 0) {
            throw new InvalidCargoInput(
                    "Груз не может не иметь формы" + System.lineSeparator()
            );
        }
        char allowedChar = input[0][0];
        for (char[] chars : input) {
            for (char c : chars) {
                if (c != ' ' && c != allowedChar) {
                    throw new InvalidCargoInput(
                            "Груз поврежден, имеется символ другого типа: " + c + System.lineSeparator()
                    );
                }
            }
        }
    }
}
