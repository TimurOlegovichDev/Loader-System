package loader.factories.cargo;

import loader.model.entites.Cargo;

public interface CargoFactory {

    Cargo createCargo(char[][] form);

}
