package loader.controllers;

import loader.model.entites.Cargo;
import loader.model.entites.Transport;
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
    private final TransportationDataContainer transportationDataContainer;
    private final CargoCounter cargoCounter;

    public InitController(TruckInitializer truckInitializer,
                          CargoInitializer cargoInitializer,
                          TransportationDataContainer transportationDataContainer,
                          CargoCounter cargoCounter) {
        this.truckInitializer = truckInitializer;
        this.cargoInitializer = cargoInitializer;
        this.transportationDataContainer = transportationDataContainer;
        this.cargoCounter = cargoCounter;
    }

    public void initializeCargos(List<String> forms) {
        List<Cargo> cargos = cargoInitializer.initialize(forms);
        transportationDataContainer.getCargoDataManager().add(cargos);
    }

    public void initializeTransport(String filePath) {
        Map<Transport, List<Cargo>> transportListMap =
                truckInitializer.initializeFromJson(filePath);
        countCargos(transportListMap);
        transportationDataContainer.getTransportDataManager().add(transportListMap);
        log.info("Транспорт добавлен в базу данных");
    }

    public void initializeTransport(int numberOfTransport) {
        List<Transport> transports =
                truckInitializer.initialize(numberOfTransport);
        transportationDataContainer.getTransportDataManager().add(transports);
    }

    private void countCargos(Map<Transport, List<Cargo>> map) {
        log.info("Информация о транспорте");
        for (Transport transport : map.keySet()) {
            List<Cargo> cargos = map.get(transport);
            cargoCounter.countCargos(cargos)
                    .forEach((symbol, count) ->
                            log.info("Тип груза: {}, количество: {}", symbol, count)
                    );
            log.info("{}{}", System.lineSeparator(), transport.toString());
        }
    }

}
