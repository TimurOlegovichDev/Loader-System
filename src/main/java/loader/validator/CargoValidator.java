package loader.validator;

import loader.exceptions.InvalidCargoInput;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CargoValidator {

    public static final Pattern NUMBERS_PATTERN = Pattern.compile("[0-9]+");
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
        // Получаем первый символ из массива формы груза,
        // так как он гарантированно содержится в массиве и мы можем понять какой тип груза нужно обработать
        char symbol = lines.get(FIRST_ROW_INDEX).charAt(FIRST_COLUMN_INDEX);
        // Возводим в квадрат, так как формат коробки это количество символов равное значению этого символа
        int expectedWeight = (int) Math.pow(Integer.parseInt(symbol + ""), 2);
        int actualWeight = 0;
        for (String line : lines) {
            if (line.isEmpty())
                continue;
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

}
