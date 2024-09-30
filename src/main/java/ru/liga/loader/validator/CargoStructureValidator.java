package ru.liga.loader.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loader.exception.InvalidCargoInput;
import ru.liga.loader.model.structure.CargoJsonStructure;

import java.util.Arrays;

@Slf4j
@Component
public class CargoStructureValidator implements Validator<CargoJsonStructure> {

    private final CharacterNeighborhoodValidator characterNeighborhoodValidator;

    @Autowired
    public CargoStructureValidator(CharacterNeighborhoodValidator characterNeighborhoodValidator) {
        this.characterNeighborhoodValidator = characterNeighborhoodValidator;
    }

    /**
     * Метод проверяет груз и бросает исключение, если груз содержит ошибку.
     *
     * @param cargo грузовая структура, полученная из json файла
     * @throws InvalidCargoInput если груз невалиден
     */

    public void validate(CargoJsonStructure cargo) throws InvalidCargoInput {
        log.debug("Валидация груза: {}", cargo.name());
        validateCargoArea(cargo);
        validateCargoForm(cargo);
        characterNeighborhoodValidator.validate(cargo);
        log.debug("Груз валиден: {}", cargo.name());
    }

    private void validateCargoArea(CargoJsonStructure cargo) throws InvalidCargoInput {
        int actualHeight = cargo.form().length;
        int actualWidth = Arrays.stream(cargo.form())
                .mapToInt(arr -> arr.length)
                .max()
                .orElse(0);
        int expectedArea = cargo.area();
        int actualArea = actualWidth * actualHeight;
        if (actualArea != expectedArea) {
            throw new InvalidCargoInput(
                    "Ожидаемыемые значения отличаются от исходных!" + System.lineSeparator() +
                            "Ожидаемый размер груза: " + expectedArea + System.lineSeparator() +
                            "Фактический размер груза: " + actualArea + System.lineSeparator() +
                            "Проверяемый груз:", cargo.name());
        }
    }

    private void validateCargoForm(CargoJsonStructure cargo) {
        char allowedChar = cargo.type();
        for (char[] chars : cargo.form()) {
            for (char c : chars) {
                if (c != ' ' && c != allowedChar) {
                    throw new InvalidCargoInput(
                            "Груз поврежден, имеется символ другого типа: " + c + System.lineSeparator() +
                                    "Проверяемый груз:", cargo.name()
                    );
                }
            }
        }
    }
}
