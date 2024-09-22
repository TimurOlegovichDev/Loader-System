package loader.algorithms;

import loader.algorithms.utils.CargoLoader;
import loader.algorithms.utils.TransportValidator;
import loader.db.CargoDataManager;
import loader.db.TransportDataManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class LoadingCargoAlgorithm {

    protected final CargoLoader cargoLoader;
    protected final TransportValidator transportValidator;

    public LoadingCargoAlgorithm(CargoLoader cargoLoader,
                                 TransportValidator transportValidator) {
        this.cargoLoader = cargoLoader;
        this.transportValidator = transportValidator;
    }

    public abstract void execute(CargoDataManager cargoDataManager, TransportDataManager transportDataManager);
}