package ru.liga.loadersystem.model.structure;

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
