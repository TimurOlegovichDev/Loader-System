package ru.liga.loader.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.factory.cargo.DefaultCargoFactory;
import ru.liga.loader.initializers.CargoInitializer;
import ru.liga.loader.initializers.TruckInitializer;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportSizeStructure;
import ru.liga.loader.repository.DefaultCrudCargoRepository;
import ru.liga.loader.repository.DefaultCrudTransportRepository;
import ru.liga.loader.util.CargoCounter;

import java.io.InputStream;
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
    private final DefaultCargoFactory defaultCargoFactory;

    @Autowired
    public InitializeService(TruckInitializer truckInitializer, CargoInitializer cargoInitializer, DefaultCrudTransportRepository transportDataRepository, DefaultCrudCargoRepository cargoDataRepository, CargoCounter cargoCounter, DefaultCargoFactory defaultCargoFactory) {
        this.truckInitializer = truckInitializer;
        this.cargoInitializer = cargoInitializer;
        this.transportDataRepository = transportDataRepository;
        this.cargoDataRepository = cargoDataRepository;
        this.cargoCounter = cargoCounter;
        this.defaultCargoFactory = defaultCargoFactory;
    }

    /**
     * Инициализирует грузы из json файла.
     * Этот метод инициализирует грузы по заданным формам и добавляет их в главный репозиторий.
     *
     * @param stream файл
     */

    public void initializeCargos(InputStream stream) {
        List<Cargo> list = cargoInitializer.initializeFromJson(stream);
        initUniqueCargo(list);
        cargoDataRepository.saveAll(list);
        log.debug("Добавлено груза: {}", list.size());
    }

    /**
     * Инициализирует транспортные средства из файла JSON.
     * Этот метод инициализирует транспортные средства из файла JSON
     * и добавляет их в менеджер данных транспортных средств.
     * Также подсчитывает количество грузов в каждом транспортном средстве из файла
     * и заносит данные в журнал
     *
     * @param stream файл JSON
     */

    public void initializeTransport(InputStream stream) {
        Map<Transport, List<Cargo>> transportMap =
                truckInitializer.initializeFromJson(stream);
        countCargos(transportMap);
        transportDataRepository.saveAll(transportMap.keySet());
        transportMap.values().forEach(list -> {
                    initUniqueCargo(list);
                    cargoDataRepository.saveAll(list);
                }
        );
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
        transportDataRepository.saveAll(transports);
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

    private void initUniqueCargo(List<Cargo> list) {
        for (Cargo cargo : list) {
            if (cargoDataRepository.findByName(cargo.getName()) == null) {
                cargoDataRepository.save(
                        defaultCargoFactory.createCargo(
                                cargo.getName(),
                                cargo.getForm()
                        )
                );
            }
        }
    }
}
