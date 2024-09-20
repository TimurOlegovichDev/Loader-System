package loader.controllers;

import loader.db.CargoData;
import loader.db.TransportData;
import loader.input.json.JsonService;
import loader.model.dto.TruckDto;
import loader.model.entites.transports.Transport;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DataController {

    private final TransportData transportData;

    private final CargoData cargoData;

    public DataController(TransportData transportData, CargoData cargoData) {
        this.transportData = transportData;
        this.cargoData = cargoData;
    }

}
