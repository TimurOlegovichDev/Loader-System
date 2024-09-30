package ru.liga.loader.validator;

import org.springframework.stereotype.Component;
import ru.liga.loader.exception.InvalidCargoInput;
import ru.liga.loader.model.structure.CargoJsonStructure;

@Component
public class CharacterNeighborhoodValidator {

    public void validate(CargoJsonStructure cargo) {
        char[][] form = cargo.form();
        for (int i = 0; i < form.length; i++) {
            for (int j = 0; j < form[i].length; j++) {
                if (form[i][j] != ' ' && !hasNeighbor(i, j, form)) {
                    throw new InvalidCargoInput("Неверная форма груза с именем: ", cargo.name());
                }
            }
        }
    }

    private boolean hasNeighbor(int i, int j, char[][] array) {
        return hasUpperNeighbor(i, j, array) ||
                hasLowerNeighbor(i, j, array) ||
                hasLeftNeighbor(i, j, array) ||
                hasRightNeighbor(i, j, array) ||
                isSingleElement(i, j, array);
    }

    private boolean hasUpperNeighbor(int i, int j, char[][] array) {
        try {
            return i > 0 && array[i - 1][j] != ' ';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean hasLowerNeighbor(int i, int j, char[][] array) {
        try {
            return i < array.length - 1 && array[i + 1][j] != ' ';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean hasLeftNeighbor(int i, int j, char[][] array) {
        try {
            return j > 0 && array[i][j - 1] != ' ';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean hasRightNeighbor(int i, int j, char[][] array) {
        try {
            return j < array[i].length - 1 && array[i][j + 1] != ' ';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean isSingleElement(int i, int j, char[][] array) {
        try {
            return i == 0 && j == 0 && array.length == 1 && array[0].length == 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}
