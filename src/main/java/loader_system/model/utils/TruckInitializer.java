package loader_system.model.utils;

import loader_system.db.Datable;
import loader_system.db.TransportData;
import loader_system.model.entites.transports.Truck;

import java.util.ArrayList;
import java.util.List;

public class TruckInitializer{

    public void initialize(int numberOfTrucks, TransportData transportData) {
        for (int i = 0; i < numberOfTrucks; i++) {
            transportData.add(new Truck());
        }
    }
}
