package ru.liga.loader.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NonNull;
import ru.liga.loader.model.structure.CargoJsonStructure;

import java.util.Arrays;

@Getter
public class Cargo {

    private final char[][] form;
    private final int height;
    private final int width;
    private final int area;
    private final String name;
    private final char type;

    public Cargo(
            @NonNull String name,
            @NonNull char[][] form) {
        this.name = name;
        this.form = form;
        this.type = form[0][0];
        height = form.length;
        width = Arrays.stream(form)
                .mapToInt(arr -> arr.length)
                .max()
                .orElse(0);
        area = height * width;
    }

    @JsonCreator
    public Cargo(CargoJsonStructure cargoJsonStructure) {
        this.name = cargoJsonStructure.getName();
        this.form = cargoJsonStructure.getForm();
        this.height = cargoJsonStructure.getHeight();
        this.width = cargoJsonStructure.getWidth();
        this.area = cargoJsonStructure.getArea();
        this.type = cargoJsonStructure.getType();
    }

    /**
     * Возвращает копию формы груза.
     * Этот метод возвращает копию формы груза, чтобы избежать изменения исходной формы.
     *
     * @return копия формы груза
     */

    public char[][] getForm() {
        return Arrays.copyOf(form, form.length);
    }

    /**
     * Возвращает строковое представление груза.
     * Этот метод возвращает строковое представление груза, которое представляет собой строку, содержащую форму груза.
     *
     * @return строковое представление груза
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator())
                .append(" название: ")
                .append(name)
                .append(System.lineSeparator())
                .append(" груз: ")
                .append(System.lineSeparator());
        for (char[] arr : form) {
            sb.append(Arrays.toString(arr));
        }
        return sb.toString();
    }
}
