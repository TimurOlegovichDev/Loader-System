package ru.liga.loader.util.initializers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.db.CargoDataManager;
import ru.liga.loader.db.TransportDataManager;
import ru.liga.loader.model.entites.Cargo;
import ru.liga.loader.model.entites.Transport;
import ru.liga.loader.util.CargoCounter;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ComponentInitializer {

    private final TruckInitializer truckInitializer;
    private final CargoInitializer cargoInitializer;
    private final TransportDataManager transportDataManager;
    private final CargoDataManager cargoDataManager;
    private final CargoCounter cargoCounter;


    public void initializeCargos(List<String> forms) {
        List<Cargo> cargos = cargoInitializer.initialize(forms);
        cargoDataManager.add(cargos);
    }

    public void initializeTransport(String filePath) {
        Map<Transport, List<Cargo>> transportListMap =
                truckInitializer.initializeFromJson(filePath);
        countCargos(transportListMap);
        transportDataManager.add(transportListMap);
        log.info("Транспорт добавлен в базу данных");
    }


    public void initializeTransport(int numberOfTransport) {
        List<Transport> transports =
                truckInitializer.initialize(numberOfTransport);
        transportDataManager.add(transports);
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
