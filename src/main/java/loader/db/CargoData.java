package loader.db;


import loader.model.entites.cargos.Cargo;

import java.util.ArrayList;
import java.util.List;

public class CargoData {

    private final List<Cargo> cargoData = new ArrayList<>();

    public List<Cargo> getData() {
        return new ArrayList<>(cargoData);
    }

    public void add(Cargo cargo) {
        cargoData.add(cargo);
    }

    public void add(List<Cargo> cargos) {
        cargoData.addAll(cargos);
    }

    public void remove(Cargo cargo) {
        cargoData.remove(cargo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cargo cargo : cargoData)
            sb.append(cargo.toString()).append("\n");
        return sb.toString();
    }
}
