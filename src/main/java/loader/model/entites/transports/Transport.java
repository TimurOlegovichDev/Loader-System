package loader.model.entites.transports;

import loader.model.entites.cargos.Cargo;

public interface Transport {

    int DEFAULT_BODY_WIDTH = 6;
    int DEFAULT_BODY_HEIGHT = 6;

    void loadCargo(Cargo cargo, int heightIndex, int widthIndex);

    void initBody();

    char[][] getBody();

    String toString();

}
