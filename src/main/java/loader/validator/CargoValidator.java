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
        log.debug("Validating box with lines: {}", lines);
        boxFormValidate(lines);
        log.debug("Box is valid: {}", lines);
    }

    private void boxFormValidate(List<String> lines) throws InvalidCargoInput {
        if (linesContainsLetter(lines)) {
            throw new InvalidCargoInput("Входные данные содержат нечисловые символы " + lines);
        }
        char symbol = lines.get(FIRST_ROW_INDEX).charAt(FIRST_COLUMN_INDEX);
        int correctWeight = (int) Math.pow(Integer.parseInt(symbol + ""), 2);
        for (String line : lines) {
            if (line.isEmpty())
                continue;
            for (char c : line.toCharArray()) {
                correctWeight -= Character.getNumericValue(c);
            }
        }
        if (correctWeight != 0) {
            throw new InvalidCargoInput("The weight of the box is different from the correct");
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
