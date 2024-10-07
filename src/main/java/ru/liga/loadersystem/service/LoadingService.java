package ru.liga.loadersystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.liga.loadersystem.algorithm.LoadingCargoAlgorithm;
import ru.liga.loadersystem.enums.AlgorithmTypes;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.processor.LoadingProcessor;
import ru.liga.loadersystem.repository.CargoCrudRepository;
import ru.liga.loadersystem.repository.DefaultCrudTransportRepository;

import java.util.List;

@Service
public class LoadingService {

    private final LoadingProcessor defaultLoadingProcessor;
    private final DefaultCrudTransportRepository transportRepository;
    private final CargoCrudRepository cargoRepository;
    private final CargoCrudRepository cargoCrudRepository;

    @Autowired
    public LoadingService(LoadingProcessor defaultLoadingProcessor,
                          DefaultCrudTransportRepository transportRepository,
                          @Qualifier("cargoCrudRepository") CargoCrudRepository cargoRepository, @Qualifier("cargoCrudRepository") CargoCrudRepository cargoCrudRepository) {
        this.defaultLoadingProcessor = defaultLoadingProcessor;
        this.transportRepository = transportRepository;
        this.cargoRepository = cargoRepository;
        this.cargoCrudRepository = cargoCrudRepository;
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
                cargoCrudRepository.findAllUnique()
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