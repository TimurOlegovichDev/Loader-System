package ru.liga.loader.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.CargoDataRepository;
import ru.liga.loader.repository.TransportDataRepository;
import ru.liga.loader.util.CargoCounter;
import ru.liga.loader.util.initializers.CargoInitializer;
import ru.liga.loader.util.initializers.TruckInitializer;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InitializeService {

    private final TruckInitializer truckInitializer;
    private final CargoInitializer cargoInitializer;
    private final TransportDataRepository transportDataRepository;
    private final CargoDataRepository cargoDataRepository;
    private final CargoCounter cargoCounter;

    @Autowired
    public InitializeService(TruckInitializer truckInitializer, CargoInitializer cargoInitializer, TransportDataRepository transportDataRepository, CargoDataRepository cargoDataRepository, CargoCounter cargoCounter) {
        this.truckInitializer = truckInitializer;
        this.cargoInitializer = cargoInitializer;
        this.transportDataRepository = transportDataRepository;
        this.cargoDataRepository = cargoDataRepository;
        this.cargoCounter = cargoCounter;
    }

    /**
     * Инициализирует грузы из json файла.
     * Этот метод инициализирует грузы по заданным формам и добавляет их в главный репозиторий.
     *
     * @param filePath путь к файлу
     */

    public void initializeCargos(String filePath) {
        Map<String, Cargo> cargoMap =
                cargoInitializer.initializeFromJson(filePath);
        cargoDataRepository.add(cargoMap);
        log.info("Груз добавлен в базу данных");
    }

    /**
     * Инициализирует транспортные средства из файла JSON.
     * Этот метод инициализирует транспортные средства из файла JSON
     * и добавляет их в менеджер данных транспортных средств.
     * Также подсчитывает количество грузов в каждом транспортном средстве из файла
     * и заносит данные в журнал
     *
     * @param filePath путь к файлу JSON
     */

    public void initializeTransport(String filePath) {
        Map<Transport, List<Cargo>> transportListMap =
                truckInitializer.initializeFromJson(filePath);
        countCargos(transportListMap);
        transportDataRepository.add(transportListMap);
        log.info("Транспорт добавлен в базу данных");
    }

    /**
     * Инициализирует транспортные средства по количеству.
     * Этот метод инициализирует транспортные средства по количеству и добавляет их в менеджер данных транспортных средств.
     *
     * @param numberOfTransport количество транспортных средств
     */

    public void initializeTransport(int numberOfTransport) {
        List<Transport> transports =
                truckInitializer.initialize(numberOfTransport);
        transportDataRepository.add(transports);
    }

    private void countCargos(Map<Transport, List<Cargo>> map) {
        log.info("Информация о транспорте");
        for (Transport transport : map.keySet()) {
            List<Cargo> cargos = map.get(transport);
            cargoCounter.countCargos(cargos)
                    .forEach((name, count) ->
                            log.info("Название груза: {}, количество: {}", name, count)
                    );
            log.info("{}{}", System.lineSeparator(), transport.toString());
        }
    }
}
