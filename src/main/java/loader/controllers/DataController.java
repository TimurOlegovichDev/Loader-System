package loader.controllers;

import loader.db.CargoData;
import loader.db.TransportData;
import lombok.Getter;

@Getter
public class DataController {

    private final TransportData transportData;

    private final CargoData cargoData;

    public DataController(TransportData transportData, CargoData cargoData) {
        this.transportData = transportData;
        this.cargoData = cargoData;
    }

}
