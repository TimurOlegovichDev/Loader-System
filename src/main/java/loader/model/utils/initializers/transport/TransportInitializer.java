package loader.model.utils.initializers.transport;

import loader.model.entites.cargos.Box;
import loader.model.entites.cargos.Cargo;
import loader.model.entites.transports.Transport;

import java.util.List;
import java.util.Map;

public interface TransportInitializer {

    List<Transport> initializeToList(int numberOfTransport);

    Map<Transport,  List<Cargo>> initializeToMapWithCargoFromFile(String filepath);
}
