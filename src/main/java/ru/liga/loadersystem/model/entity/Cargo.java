package ru.liga.loadersystem.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;
import ru.liga.loadersystem.model.json.CargoJsonStructure;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.UUID;

@Getter
@Entity
@Table(schema = "cargo", name = "cargo")
public class Cargo {

    @Id
    private final UUID id;
    private String form;
    private int height;
    private int width;
    private int area;
    private char type;
    @Setter
    private String name;

    @Nullable
    @Setter
    private UUID transportId;

    public Cargo(@NonNull String name, String form) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.form = form;
        this.type = form.charAt(0);
        this.height = countLines(form);
        this.width = countMaxWidth(form);
        this.area = height * width;
    }

    @JsonCreator
    public Cargo(CargoJsonStructure cargoJsonStructure) {
        this.name = cargoJsonStructure.name();
        this.id = cargoJsonStructure.id();
        this.form = cargoJsonStructure.form();
        this.height = cargoJsonStructure.height();
        this.width = cargoJsonStructure.width();
        this.area = cargoJsonStructure.area();
        this.type = cargoJsonStructure.type();
        this.transportId = cargoJsonStructure.transportId();
    }

    public Cargo() {
        name = "Noname";
        form = "Noform";
        height = 0;
        width = 0;
        area = 0;
        type = 0;
        this.id = UUID.randomUUID();
    }

    public void setForm(String form) {
        this.form = form;
        this.type = form.charAt(0);
        this.height = countLines(form);
        this.width = countMaxWidth(form);
        this.area = height * width;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String lines : form.split(";")) {
            builder.append(lines).append(System.lineSeparator());
        }
        return System.lineSeparator() +
                "Название груза: " +
                name +
                System.lineSeparator() +
                "Форма: " +
                System.lineSeparator() +
                builder;
    }

    @JsonIgnore
    public char[][] getCharForm() {
        char[][] result = new char[height][];
        int i = 0;
        for (String lines : form.split(";")) {
            result[i++] = lines.toCharArray();
        }
        return result;
    }

    private int countLines(String form) {
        return form.split(";").length;
    }

    private int countMaxWidth(String form) {
        return Arrays.stream(form.split(";"))
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }
}