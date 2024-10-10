package ru.liga.loadersystem.model.json;

import java.util.UUID;

public record CargoJsonStructure(
        UUID id,
        UUID transportId,
        String name,
        String form,
        int height,
        int width,
        int area,
        char type) {
}
