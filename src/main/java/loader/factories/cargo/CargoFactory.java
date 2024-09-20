package loader.factories.cargo;

import loader.model.entites.cargos.Cargo;

public class CargoFactory {

    public Cargo createCargo(char[][] form) {
        return new Cargo(form);
    }
}
