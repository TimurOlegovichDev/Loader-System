package loader_system.model.factories.cargo;

import loader_system.model.entites.cargos.Box;
import loader_system.model.entites.cargos.Cargo;

public class BoxFactory implements CargoFactory {

    @Override
    public Cargo createCargo(char[][] form) {
        return new Box(form);
    }
}
