package loader_system.db;

import loader_system.model.entites.cargos.Cargo;
import java.util.ArrayList;
import java.util.List;

public class CargoData implements Datable {

    private final List<Cargo> cargoData = new ArrayList<>();

    @Override
    public List<Cargo> getData() {
        return new ArrayList<>(cargoData);
    }

    @Override
    public void add(Object o) {
        cargoData.add((Cargo) o);
    }

    @Override
    public void remove(Object o) {
        cargoData.remove((Cargo) o);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Cargo cargo : cargoData)
            sb.append(cargo.toString()).append("\n");
        return sb.toString();
    }
}
