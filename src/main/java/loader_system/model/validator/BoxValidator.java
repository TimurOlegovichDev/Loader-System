package loader_system.model.validator;

import loader_system.model.exceptions.InvalidCargoInput;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class BoxValidator {

    public static final Pattern NUMBERS_PATTERN = Pattern.compile("[0-9]+");

    public void validate(List<String> lines) {
        log.debug("Validating box with lines: {}", lines);
        checkLinesForNotNumbers(lines);
        boxFormValidate(lines);
        log.debug("Box is valid: {}", lines);
    }

    private void checkLinesForNotNumbers(List<String> lines) throws InvalidCargoInput {
        for (String line : lines) {
            Matcher matcher = NUMBERS_PATTERN.matcher(line);
            if (!matcher.matches()) {
                throw new InvalidCargoInput("The box data invalid");
            }
        }
    }

    private void boxFormValidate(List<String> lines) throws InvalidCargoInput {
        char symbol = lines.get(0).charAt(0);
        int weight = (int) Math.pow(Integer.parseInt(symbol + ""), 2);
        for (String line : lines) {
            if (line.isEmpty())
                continue;
            for (char c : line.toCharArray()) {
                if (c != symbol) {
                    throw new InvalidCargoInput("The box format is damaged");
                }
                weight -= Integer.parseInt(c + "");
            }
        }
        if (weight != 0) {
            throw new InvalidCargoInput("The box weight is damaged");
        }
    }
}
