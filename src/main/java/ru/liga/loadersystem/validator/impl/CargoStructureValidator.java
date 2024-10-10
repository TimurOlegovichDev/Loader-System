package ru.liga.loadersystem.validator.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loadersystem.exception.InvalidCargoInput;
import ru.liga.loadersystem.model.json.CargoJsonStructure;
import ru.liga.loadersystem.validator.Validator;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CargoStructureValidator implements Validator<CargoJsonStructure> {

    private final CharacterNeighborhoodValidator characterNeighborhoodValidator;

    /**
     * Метод проверяет груз и бросает исключение, если груз содержит ошибку.
     *
     * @param cargo грузовая структура, полученная из json файла
     * @throws InvalidCargoInput если груз невалиден
     */
    @Override
    public void validate(CargoJsonStructure cargo) throws InvalidCargoInput {
        log.debug("Валидация груза: {}", cargo.name());
        validateType(cargo);
        validateCargoArea(cargo);
        validateCargoForm(cargo);
        characterNeighborhoodValidator.validate(cargo);
        log.debug("Груз валиден: {}", cargo.name());
    }

    private void validateType(CargoJsonStructure cargo) throws InvalidCargoInput {
        if (cargo.type() == ' ') {
            throw new InvalidCargoInput(
                    "Коробка такого типа не может существовать!", cargo.name()
            );
        }
    }

    private void validateCargoArea(CargoJsonStructure cargo) throws InvalidCargoInput {
        int actualHeight = cargo.form().split(";").length;
        int actualWidth = Arrays.stream(cargo.form().split(";"))
                .mapToInt(String::length)
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
        for (String chars : cargo.form().split(";")) {
            for (char c : chars.toCharArray()) {
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
