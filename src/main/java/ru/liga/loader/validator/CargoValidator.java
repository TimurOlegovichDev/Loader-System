package ru.liga.loader.validator;

import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.exception.InvalidCargoInput;
import ru.liga.loader.model.structure.CargoJsonStructure;

import java.util.Arrays;

@Slf4j
public class CargoValidator {

    /**
     * Метод проверяет груз и бросает исключение, если груз содержит ошибку.
     *
     * @param cargo грузовая структура, полученная из json файла
     * @throws InvalidCargoInput если груз невалиден
     */

    public void validate(CargoJsonStructure cargo) throws InvalidCargoInput {
        log.debug("Валидация груза: {}", cargo.getName());
        validateCargoArea(cargo);
        validateCargoForm(cargo);
        checkNeighborhood(cargo);
        log.debug("Груз валиден: {}", cargo.getName());
    }

    private void validateCargoArea(CargoJsonStructure cargo) throws InvalidCargoInput {
        int actualHeight = cargo.getForm().length;
        int actualWidth = Arrays.stream(cargo.getForm())
                .mapToInt(arr -> arr.length)
                .max()
                .orElse(0);
        int expectedArea = cargo.getArea();
        int actualArea = actualWidth * actualHeight;
        if (actualArea != expectedArea) {
            throw new InvalidCargoInput(
                    "Ожидаемыемые значения отличаются от исходных!" + System.lineSeparator() +
                            "Ожидаемый размер груза: " + expectedArea + System.lineSeparator() +
                            "Фактический размер груза: " + actualArea + System.lineSeparator() +
                            "Проверяемый груз:", cargo.getName());
        }
    }

    private void validateCargoForm(CargoJsonStructure cargo) {
        char allowedChar = cargo.getType();
        for (char[] chars : cargo.getForm()) {
            for (char c : chars) {
                if (c != ' ' && c != allowedChar) {
                    throw new InvalidCargoInput(
                            "Груз поврежден, имеется символ другого типа: " + c + System.lineSeparator() +
                                    "Проверяемый груз:", cargo.getName()
                    );
                }
            }
        }
    }

    private void checkNeighborhood(CargoJsonStructure cargo) {
        char[][] array = cargo.getForm();
        if (array.length == 1 && array[0].length == 1) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != ' ') {
                    boolean hasNeighbor = i > 0 && array[i - 1][j] != ' ';
                    if (i < array.length - 1 && array[i + 1][j] != ' ') hasNeighbor = true;
                    if (j > 0 && array[i][j - 1] != ' ') hasNeighbor = true;
                    if (j < array[i].length - 1 && array[i][j + 1] != ' ') hasNeighbor = true;
                    if (!hasNeighbor) {
                        throw new InvalidCargoInput(
                                "Неправильная форма груза!" + System.lineSeparator() +
                                        "Проверяемый груз:", cargo.getName()
                        );
                    }
                }
            }
        }
    }
}
