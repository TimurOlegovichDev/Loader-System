package loader.model.factories.cargo;

import loader.model.entites.cargos.Box;
import loader.model.entites.cargos.Cargo;

public class BoxFactory implements CargoFactory {

    @Override
    public Cargo createCargo(char[][] form) {
        return new Box(form);
    }
}
