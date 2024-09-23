package ru.liga.loader.model.structure;

import ru.liga.loader.model.entity.Cargo;

import java.util.List;

public record TransportJsonStructure(char[][] body, List<Cargo> cargos) {

    public char[][] getBody() {
        return this.body;
    }

    public List<Cargo> getCargos() {
        return this.cargos;
    }
}
