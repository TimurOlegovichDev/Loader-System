package ru.liga.loader.model.structure;

public record CargoJsonStructure(
        String name,
        char[][] form,
        int height,
        int width,
        int area,
        char type) {
}
