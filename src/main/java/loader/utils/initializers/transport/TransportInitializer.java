package loader.utils.initializers.transport;

import loader.entites.cargos.Cargo;
import loader.entites.transports.Transport;

import java.util.List;
import java.util.Map;

public interface TransportInitializer {

    List<Transport> initializeToList(int numberOfTransport);

    Map<Transport,  List<Cargo>> initializeToMapWithCargoFromFile(String filepath);
}
