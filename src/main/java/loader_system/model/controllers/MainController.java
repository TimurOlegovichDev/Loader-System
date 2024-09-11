package loader_system.model.controllers;

import loader_system.db.CargoData;
import loader_system.db.TransportData;

public class MainController {

    private final TransportData transportData;

    private final CargoData cargoData;

    public MainController() {
        this.transportData = new TransportData();
        this.cargoData = new CargoData();
    }

    public void start(){

    }

}
