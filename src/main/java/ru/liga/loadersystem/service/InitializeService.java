package ru.liga.loadersystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loadersystem.factory.cargo.DefaultCargoFactory;
import ru.liga.loadersystem.initializers.CargoInitializer;
import ru.liga.loadersystem.initializers.TruckInitializer;
import ru.liga.loadersystem.model.dto.TransportDto;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.repository.CargoCrudRepository;
import ru.liga.loadersystem.repository.TransportCrudRepository;
import ru.liga.loadersystem.util.CargoCounter;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class InitializeService {

    private final TruckInitializer truckInitializer;
    private final CargoInitializer cargoInitializer;
    private final TransportCrudRepository transportDataRepository;
    private final CargoCrudRepository cargoDataRepository;
    private final CargoCounter cargoCounter;
    private final DefaultCargoFactory defaultCargoFactory;

    /**
     * Инициализирует грузы из json файла.
     * Этот метод инициализирует грузы по заданным формам и добавляет их в главный репозиторий.
     *
     * @param stream файл
     */
    public void initializeCargos(InputStream stream) throws Exception {
        List<Cargo> list = cargoInitializer.initializeFromJson(stream);
        initUniqueCargo(list);
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
    public void initializeTransport(InputStream stream) throws Exception {
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
    public int initializeTransport(List<TransportDto> list) {
        List<Transport> transports =
                truckInitializer.initialize(list);
        transportDataRepository.saveAll(transports);
        log.debug("Добавлено транспорта из полученных размеров: {}", transports.size());
        return transports.size();
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
