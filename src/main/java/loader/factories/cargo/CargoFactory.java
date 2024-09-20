package loader.factories.cargo;

import loader.entites.cargos.Cargo;

public interface CargoFactory {

    Cargo createCargo(char[][] form);

}
