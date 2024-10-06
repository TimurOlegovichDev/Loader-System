package ru.liga.loader.model.structure;

import ru.liga.loader.model.entity.Cargo;

import java.util.List;
import java.util.UUID;

public record TransportJsonStructure(
        UUID id,
        String body,
        List<Cargo> cargos
) {
}
