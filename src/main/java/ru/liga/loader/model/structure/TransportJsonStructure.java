package ru.liga.loader.model.structure;

import ru.liga.loader.model.entity.Cargo;

import java.util.List;

public record TransportJsonStructure(
        String id,
        char[][] body,
        List<Cargo> cargos
) {
}
