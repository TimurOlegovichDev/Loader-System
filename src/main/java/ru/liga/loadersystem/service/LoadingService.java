package ru.liga.loadersystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loadersystem.algorithm.LoadingCargoAlgorithm;
import ru.liga.loadersystem.enums.AlgorithmTypes;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.processor.LoadingProcessor;
import ru.liga.loadersystem.repository.CargoCrudRepository;
import ru.liga.loadersystem.repository.TransportCrudRepository;

import java.util.List;

@Service
public class LoadingService {

    private final LoadingProcessor defaultLoadingProcessor;
    private final TransportCrudRepository transportRepository;
    private final CargoCrudRepository cargoRepository;

    @Autowired
    public LoadingService(LoadingProcessor defaultLoadingProcessor,
                          TransportCrudRepository transportRepository,
                          CargoCrudRepository cargoRepository) {
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
        defaultLoadingProcessor.process(
                getAlgorithm(algoName),
                (List<Transport>) transportRepository.findAll(),
                cargoRepository.findAllUnique()
        );
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
                getAlgorithm(algoName),
                transports,
                cargos
        );
    }

    private LoadingCargoAlgorithm getAlgorithm(String algoName) {
        return AlgorithmTypes.of(
                algoName,
                cargoRepository,
                transportRepository
        );
    }

    /**
     * Перезагружает грузы в транспортные средства с использованием алгоритма с указанным именем.
     *
     * @param algoName имя алгоритма
     */

    public void reload(String algoName) {
        List<Cargo> cargoToLoad = (List<Cargo>) cargoRepository.findAllLoaded();
        for (Transport transport : transportRepository.findAll()) {
            transport.unloadAll();
            transportRepository.save(transport);
            cargoRepository.deleteAllByTransportId(transport.getId());
        }
        selectiveLoad(
                algoName,
                (List<Transport>) transportRepository.findAll(),
                cargoToLoad
        );
    }
}