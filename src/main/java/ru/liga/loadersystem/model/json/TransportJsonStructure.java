package ru.liga.loadersystem.model.json;

import ru.liga.loadersystem.model.entity.Cargo;

import java.util.List;
import java.util.UUID;

public record TransportJsonStructure(
        UUID id,
        String body,
        List<Cargo> cargos
) {
}
