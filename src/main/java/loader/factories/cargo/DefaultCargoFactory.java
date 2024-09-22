package loader.factories.cargo;

import loader.model.entites.Cargo;

public class DefaultCargoFactory implements CargoFactory {

    @Override
    public Cargo createCargo(char[][] form) {
        return new Cargo(form);
    }
}
