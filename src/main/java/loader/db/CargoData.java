package loader.db;

import loader.model.entites.cargos.Cargo;
import java.util.ArrayList;
import java.util.List;

public class CargoData {

    private final List<Cargo> cargoData = new ArrayList<>();


    public List<Cargo> getData() {
        return new ArrayList<>(cargoData);
    }


    public void add(Object o) {
        cargoData.add((Cargo) o);
    }


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
