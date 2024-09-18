package loader.model.factories.cargo;

import loader.model.entites.cargos.Cargo;

public interface CargoFactory {

    Cargo createCargo(char[][] form);

}
