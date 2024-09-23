package ru.liga.loader.db;


import ru.liga.loader.model.entites.Cargo;

import java.util.ArrayList;
import java.util.List;

public class CargoDataManager {

    private final List<Cargo> cargoData;

    public CargoDataManager(List<Cargo> cargoData) {
        this.cargoData = cargoData;
    }

    public List<Cargo> getData() {
        return new ArrayList<>(cargoData);
    }

    public void add(Cargo cargo) {
        cargoData.add(cargo);
    }

    public void add(List<Cargo> cargos) {
        cargoData.addAll(cargos);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cargo cargo : cargoData)
            sb.append(cargo.toString()).append("\n");
        return sb.toString();
    }
}
