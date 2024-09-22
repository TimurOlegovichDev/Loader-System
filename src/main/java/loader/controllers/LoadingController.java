package loader.controllers;

import loader.db.CargoDataManager;
import loader.db.TransportDataManager;

public interface LoadingController {
    void startLoading(CargoDataManager cargoDataManagerSrc,
                      TransportDataManager transportDataManagerDest,
                      String algorithmName);
}
