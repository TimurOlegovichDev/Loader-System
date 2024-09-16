package loader_system.model.utils.initializers;

import loader_system.db.TransportData;
import loader_system.model.entites.transports.Truck;

public class TruckInitializer{

    public void initialize(int numberOfTrucks, TransportData transportData) {
        for (int i = 0; i < numberOfTrucks; i++) {
            transportData.add(new Truck());
        }
    }
}
