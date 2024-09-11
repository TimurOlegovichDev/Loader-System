package loader_system.model.entites.transports;

import loader_system.model.entites.cargos.Cargo;

public interface Transport {

    int DEFAULT_BODY_WIDTH = 6;
    int DEFAULT_BODY_HEIGHT = 6;

    void loadCargo(Cargo cargo);

    boolean validCargo(Cargo cargo);

    void initBody();

    char[][] getBody();

}
