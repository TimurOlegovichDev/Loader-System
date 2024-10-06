package ru.liga.loader.model.structure;

public record CargoJsonStructure(
        String name,
        String form,
        int height,
        int width,
        int area,
        char type) {
}
