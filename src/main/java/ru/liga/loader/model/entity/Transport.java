package ru.liga.loader.model.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Transport {

    private final int DEFAULT_BODY_WIDTH = 6;
    private final int DEFAULT_BODY_HEIGHT = 6;

    @Getter
    private final char[][] body;

    public Transport() {
        body = new char[DEFAULT_BODY_HEIGHT][DEFAULT_BODY_WIDTH];
        Arrays.stream(body).forEach(row -> Arrays.fill(row, ' '));
    }

    public Transport(char[][] body) {
        this.body = body;
    }

    /**
     * Возвращает строковое представление транспортного средства.
     * Этот метод возвращает строковое представление транспортного средства, которое представляет собой строку, содержащую кузов транспортного средства.
     *
     * @return строковое представление кузова транспортного средства
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] arr : body) {
            sb.append("+");
            for (char c : arr) {
                sb.append(c);
            }
            sb.append("+")
                    .append(System.lineSeparator());
        }
        sb.append("+".repeat(body[0].length + 2))
                .append(System.lineSeparator());
        return sb.toString();
    }
}
