package loader_system.model.algorithms;

import loader_system.db.CargoData;
import loader_system.db.TransportData;

public interface Algorithm {

    void execute(CargoData cargoData, TransportData transportData);

}
