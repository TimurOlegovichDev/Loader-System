package ru.liga.loader.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportSizeStructure;
import ru.liga.loader.repository.impl.DefaultCrudCargoRepository;
import ru.liga.loader.repository.impl.DefaultCrudTransportRepository;
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
    private final DefaultCrudTransportRepository transportDataRepository;
    private final DefaultCrudCargoRepository cargoDataRepository;
    private final CargoCounter cargoCounter;

    @Autowired
    public InitializeService(TruckInitializer truckInitializer, CargoInitializer cargoInitializer, DefaultCrudTransportRepository transportDataRepository, DefaultCrudCargoRepository cargoDataRepository, CargoCounter cargoCounter) {
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
        cargoDataRepository.addAll(cargoMap);
        log.debug("Добавлено груза: {}", cargoMap.size());
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
        Map<Transport, List<Cargo>> transportMap =
                truckInitializer.initializeFromJson(filePath);
        countCargos(transportMap);
        transportDataRepository.addAll(transportMap);
        for (Transport transport : transportMap.keySet()) {
            for (Cargo cargo : transportMap.get(transport)) {
                cargoDataRepository.add(cargo);
            }
        }
        log.debug("Добавлено транспорта из файла: {}", transportMap.size());
    }

    /**
     * Инициализирует транспортные средства по размерам.
     *
     * @param list список размеров
     */

    public void initializeTransport(List<TransportSizeStructure> list) {
        List<Transport> transports =
                truckInitializer.initialize(list);
        transportDataRepository.add(transports);
        log.debug("Добавлено транспорта из полученных размеров: {}", transports.size());
    }

    private void countCargos(Map<Transport, List<Cargo>> map) {
        log.info("Информация о транспорте");
        for (Transport transport : map.keySet()) {
            List<Cargo> cargos = map.get(transport);
            cargoCounter.count(cargos)
                    .forEach((name, count) ->
                            log.info("Название груза: {}, количество: {}", name, count)
                    );
            log.info("{}{}", System.lineSeparator(), transport.toString());
        }
    }
}
