package loader_system.model.entites.transports;

import loader_system.db.TransportData;
import loader_system.model.entites.cargos.Cargo;

import java.util.List;

public interface Transport {

    int DEFAULT_BODY_WIDTH = 6;
    int DEFAULT_BODY_HEIGHT = 6;

    void loadCargo(Cargo cargo, int heightIndex, int widthIndex);

    void initBody();

    char[][] getBody();

}
