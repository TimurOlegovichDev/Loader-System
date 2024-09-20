package loader.controllers;

import loader.db.CargoData;
import loader.db.TransportData;
import lombok.Getter;

@Getter
public class Repository {

    private final TransportData transportData;

    private final CargoData cargoData;

    public Repository(TransportData transportData, CargoData cargoData) {
        this.transportData = transportData;
        this.cargoData = cargoData;
    }

}
