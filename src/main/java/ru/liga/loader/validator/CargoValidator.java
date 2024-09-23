package ru.liga.loader.validator;

import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.exception.InvalidCargoInput;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CargoValidator {

    private final Pattern NUMBERS_PATTERN = Pattern.compile("[0-9]+");
    private final int FIRST_ROW_INDEX = 0;
    private final int FIRST_COLUMN_INDEX = 0;

    public void validate(List<String> lines) throws InvalidCargoInput {
        log.debug("Валидация коробки: {}", lines);
        boxFormValidate(lines);
        log.debug("Коробка валидна: {}", lines);
    }

    private void boxFormValidate(List<String> lines) throws InvalidCargoInput {
        if (linesContainsLetter(lines)) {
            throw new InvalidCargoInput("Входные данные содержат нечисловые символы " + lines);
        }
        char symbol = lines.get(FIRST_ROW_INDEX).charAt(FIRST_COLUMN_INDEX);
        int expectedWeight = getWeight(symbol);
        int actualWeight = 0;
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            for (char c : line.toCharArray()) {
                actualWeight += Character.getNumericValue(c);
            }
        }
        if (actualWeight != expectedWeight) {
            throw new InvalidCargoInput("Вес коробки отличается от ожидаемого: текущий: "
                    + actualWeight + " ожидаемый: "
                    + expectedWeight);
        }
    }

    private boolean linesContainsLetter(List<String> lines) throws InvalidCargoInput {
        for (String line : lines) {
            Matcher matcher = NUMBERS_PATTERN.matcher(line);
            if (!matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    private int getWeight(char symbol) {
        return (int) Math.pow(Character.getNumericValue(symbol), 2);
    }
}
