package ru.liga.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.processor.LoadingProcessor;
import ru.liga.loader.repository.CargoCrudRepository;
import ru.liga.loader.repository.TransportCrudRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoadingService {

    private final LoadingProcessor defaultLoadingProcessor;
    private final TransportCrudRepository transportRepository;
    private final CargoCrudRepository cargoRepository;

    @Autowired
    public LoadingService(LoadingProcessor defaultLoadingProcessor, TransportCrudRepository transportRepository, CargoCrudRepository cargoRepository) {
        this.defaultLoadingProcessor = defaultLoadingProcessor;
        this.transportRepository = transportRepository;
        this.cargoRepository = cargoRepository;
    }

    /**
     * Загружает грузы в транспортные средства с использованием алгоритма с указанным именем.
     *
     * @param algoName имя алгоритма
     */

    public void load(String algoName) {
        defaultLoadingProcessor.process(algoName);
    }

    /**
     * Загружает определенный список грузов в определенные транспортные средства с использованием алгоритма с указанным именем.
     *
     * @param algoName   имя алгоритма
     * @param transports список транспортных средств
     * @param cargos     список грузов
     */

    public void selectiveLoad(String algoName,
                              List<Transport> transports,
                              List<Cargo> cargos) {
        defaultLoadingProcessor.process(
                algoName,
                transports,
                cargos
        );
    }

    /**
     * Перезагружает грузы в транспортные средства с использованием алгоритма с указанным именем.
     *
     * @param algoName имя алгоритма
     */

    public void reload(String algoName) {
        for (Transport transport : transportRepository.getKeys()) {
            transport.unloadAll();
        }
        List<Cargo> cargosToLoad = new ArrayList<>();
        for (Cargo cargo : transportRepository.getAllCargos()) {
            if (cargoRepository.getKeys().contains(cargo.getName())) {
                cargosToLoad.add(cargoRepository.getBy(cargo.getName()));
            }
        }
        transportRepository.unloadAllCargo();
        selectiveLoad(
                algoName,
                transportRepository.getKeys(),
                cargosToLoad
        );
    }
}