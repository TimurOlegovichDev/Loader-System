package ru.liga.loader.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.algorithm.LoadingCargoAlgorithm;
import ru.liga.loader.enums.AlgorithmTypes;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.processor.LoadingProcessor;
import ru.liga.loader.repository.impl.DefaultCrudCargoRepository;
import ru.liga.loader.repository.impl.DefaultCrudTransportRepository;

import java.util.List;

@Slf4j
@Service
public class DefaultLoadingProcessor implements LoadingProcessor {

    private final DefaultCrudTransportRepository transportDataRepository;
    private final DefaultCrudCargoRepository cargoDataRepository;

    @Autowired
    public DefaultLoadingProcessor(DefaultCrudTransportRepository transportDataRepository,
                                   DefaultCrudCargoRepository cargoDataRepository) {
        this.transportDataRepository = transportDataRepository;
        this.cargoDataRepository = cargoDataRepository;
    }

    /**
     * Обрабатывает загрузку грузов по указанному алгоритму.
     * Этот метод получает алгоритм загрузки грузов по указанному имени и выполняет его.
     *
     * @param algorithmName имя алгоритма загрузки грузов
     */

    @Override
    public void process(String algorithmName) {
        process(
                getAlgorithm(
                        algorithmName,
                        transportDataRepository.getKeys(),
                        cargoDataRepository.getAll()
                )
        );
    }

    @Override
    public void process(String algorithmName,
                        List<Transport> transports,
                        List<Cargo> cargos) {
        process(
                getAlgorithm(
                        algorithmName,
                        transports,
                        cargos
                )
        );
    }


    /**
     * Загружает грузы в транспортные средства по указанному алгоритму.
     * Этот метод выполняет алгоритм загрузки грузов и загружает грузы в транспортные средства.
     *
     * @param algorithm алгоритм загрузки грузов
     */

    @Override
    public void process(LoadingCargoAlgorithm algorithm) {
        algorithm.execute();
    }

    private LoadingCargoAlgorithm getAlgorithm(String algorithmName,
                                               List<Transport> transports,
                                               List<Cargo> cargos) {
        switch (AlgorithmTypes.of(algorithmName)) {
            case EL -> {
                return AlgorithmTypes.createElAlgorithm(
                        transportDataRepository,
                        transports,
                        cargos
                );
            }
            case MES -> {
                return AlgorithmTypes.createMesAlgorithm(
                        transportDataRepository,
                        transports,
                        cargos
                );
            }
        }
        return AlgorithmTypes.createMesAlgorithm(
                transportDataRepository,
                transports,
                cargos
        );
    }
}
