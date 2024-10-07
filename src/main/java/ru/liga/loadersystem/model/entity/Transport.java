package ru.liga.loadersystem.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.liga.loadersystem.model.structure.TransportJsonStructure;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Getter
@Slf4j
@Entity
@Table(schema = "transport", name = "transport")
public class Transport {

    @Id
    private UUID id = UUID.randomUUID();

    @Setter
    private String body;

    public Transport(int bodyWidth, int bodyHeight) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bodyHeight; i++) {
            sb.append(" ".repeat(Math.max(0, bodyWidth)));
            sb.append(";");
        }
        body = sb.toString();
        id = UUID.randomUUID();
    }

    @JsonCreator
    public Transport(TransportJsonStructure structure) {
        this.id = structure.id();
        this.body = structure.body();
    }

    public Transport(UUID id, String body) {
        this.id = id;
        this.body = body;
    }

    public Transport() {
        body = "Nobody";
    }

    public char[][] getCharBody() {
        char[][] result = new char[body.split(";").length][];
        int i = 0;
        for (String lines : body.split(";")) {
            result[i++] = lines.toCharArray();
        }
        return result;
    }

    /**
     * Возвращает графическое представление транспорта в изначальное состояние
     */

    public void unloadAll() {
        body = body.replaceAll("[^;]", " ");
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
        for (String line : body.split(";")) {
            sb.append("+");
            sb.append(line);
            sb.append("+")
                    .append(System.lineSeparator());
        }
        sb.append("+".repeat(body.split(";")[0].length() + 2))
                .append(System.lineSeparator());
        return sb.toString();
    }

    /**
     * Проверка на то, что груз может поместится в грузовик
     *
     * @param cargo - груз для проверки
     * @return true, если погрузка возможна, false в ином случае
     */

    public boolean canBeLoaded(Cargo cargo) {
        int bodyHeight = body.split(";").length;
        int bodyWidth = body.split(";")[0].length();
        return cargo.getHeight() <= bodyHeight + 1
                && cargo.getWidth() <= bodyWidth + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transport transport = (Transport) o;
        return Objects.equals(id, transport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}