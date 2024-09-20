package loader.factories.cargo;

import loader.entites.cargos.Box;
import loader.entites.cargos.Cargo;

public class BoxFactory implements CargoFactory {

    @Override
    public Cargo createCargo(char[][] form) {
        return new Box(form);
    }
}
