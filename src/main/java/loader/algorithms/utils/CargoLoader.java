package loader.algorithms.utils;

import loader.model.entites.Cargo;
import loader.model.entites.Transport;

public interface CargoLoader {

    void loadCargo(Cargo cargo, Transport transport, int heightIndex, int widthIndex);

    boolean canLoadCargo(Cargo cargo, Transport transport);

    void tryLoadToTransport(Cargo cargo, Transport transport);
}