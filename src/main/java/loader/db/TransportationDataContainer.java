package loader.db;

import lombok.Getter;

@Getter
public class TransportationDataContainer {

    private final TransportDataManager transportDataManager;

    private final CargoDataManager cargoDataManager;

    public TransportationDataContainer(TransportDataManager transportDataManager, CargoDataManager cargoDataManager) {
        this.transportDataManager = transportDataManager;
        this.cargoDataManager = cargoDataManager;
    }
}
