package loader.controllers;

import loader.model.entites.cargos.Cargo;
import loader.model.entites.transports.Transport;
import loader.utils.CargoCounter;
import loader.utils.initializers.CargoInitializer;
import loader.utils.initializers.TruckInitializer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class InitController {

    private final TruckInitializer truckInitializer;
    private final CargoInitializer cargoInitializer;
    private final Repository repository;
    private final CargoCounter cargoCounter;

    public InitController(TruckInitializer truckInitializer,
                          CargoInitializer cargoInitializer,
                          Repository repository,
                          CargoCounter cargoCounter) {
        this.truckInitializer = truckInitializer;
        this.cargoInitializer = cargoInitializer;
        this.repository = repository;
        this.cargoCounter = cargoCounter;
    }

    public void initializeCargos(List<String> forms) {
        List<Cargo> cargos = cargoInitializer.initialize(forms);
        repository.getCargoData().add(cargos);
    }

    public void initializeTransport(String filePath) {
        Map<Transport, List<Cargo>> transportListMap =
                truckInitializer.initializeFromJson(filePath);
        countCargos(transportListMap);
        repository.getTransportData().add(transportListMap);
    }

    public void initializeTransport(int numberOfTransport) {
        List<Transport> transports =
                truckInitializer.initialize(numberOfTransport);
        repository.getTransportData().add(transports);
    }

    private void countCargos(Map<Transport, List<Cargo>> map) {
        for (Transport transport : map.keySet()) {
            List<Cargo> cargos = map.get(transport);
            cargoCounter.countCargos(cargos)
                    .forEach((symbol, count) ->
                            log.info("Truck contains {} cargos {} type", count, symbol)
                    );
            log.info("{}{}", System.lineSeparator(), transport.toString());
        }
    }

}
