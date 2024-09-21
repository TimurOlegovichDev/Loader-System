package loader.algorithms;

import loader.algorithms.utils.CargoLoader;
import loader.algorithms.utils.CargoSorter;
import loader.algorithms.utils.TransportValidator;
import loader.db.CargoData;
import loader.db.TransportData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class LoadingCargoAlgorithm {
    protected final CargoLoader cargoLoader;
    protected final CargoSorter cargoSorter;
    protected final TransportValidator transportValidator;

    public LoadingCargoAlgorithm(CargoLoader cargoLoader,
                                 CargoSorter cargoSorter,
                                 TransportValidator transportValidator) {
        this.cargoLoader = cargoLoader;
        this.cargoSorter = cargoSorter;
        this.transportValidator = transportValidator;
    }

    public abstract void execute(CargoData cargoData, TransportData transportData);

}