package ru.liga.loader.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.model.structure.TransportJsonStructure;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Getter
@Slf4j
public class Transport {

    private final char[][] body;
    private final String id;

    public Transport(int bodyWidth, int bodyHeight) {
        body = new char[bodyHeight][bodyWidth];
        id = UUID.randomUUID().toString();
        unloadAll();
    }

    @JsonCreator
    public Transport(TransportJsonStructure structure) {
        this.id = structure.id();
        this.body = structure.body();
    }

    public Transport(String id, char[][] body) {
        this.id = id;
        this.body = body;
    }

    public void unloadAll() {
        Arrays.stream(body).forEach(row -> Arrays.fill(row, ' '));
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
        sb.append("ID транспорта: ")
                .append(id)
                .append(System.lineSeparator());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transport transport = (Transport) o;
        return Objects.equals(id, transport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
