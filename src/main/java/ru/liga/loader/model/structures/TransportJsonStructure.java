package ru.liga.loader.model.structures;

import ru.liga.loader.model.entites.Cargo;

import java.util.List;

public record TransportJsonStructure(char[][] body, List<Cargo> cargos) {

    public char[][] getBody() {
        return this.body;
    }

    public List<Cargo> getCargos() {
        return this.cargos;
    }
}
