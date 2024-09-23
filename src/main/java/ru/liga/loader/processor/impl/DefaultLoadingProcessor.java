package ru.liga.loader.processor.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.algorithm.LoadingCargoAlgorithm;
import ru.liga.loader.db.CargoDataManager;
import ru.liga.loader.db.TransportDataManager;
import ru.liga.loader.enums.AlgorithmTypes;
import ru.liga.loader.processor.LoadingProcessor;

@Slf4j
@RequiredArgsConstructor
public class DefaultLoadingProcessor implements LoadingProcessor {

    private final TransportDataManager transportDataManager;
    private final CargoDataManager cargoDataManager;

    @Override
    public void process(String algorithmName) {
        load(getAlgorithm(algorithmName));
    }

    public void load(LoadingCargoAlgorithm algorithm) {
        try {
            algorithm.execute();
        } catch (Exception e) {
            log.error("Процесс погрузки прерван: {}", e.getMessage());
        }
    }

    public LoadingCargoAlgorithm getAlgorithm(String algorithmName) {
        switch (AlgorithmTypes.of(algorithmName)) {
            case EL -> {
                return AlgorithmTypes.createElAlgorithm(
                        transportDataManager,
                        cargoDataManager
                );
            }
            case MES -> {
                return AlgorithmTypes.createMesAlgorithm(
                        transportDataManager,
                        cargoDataManager
                );
            }
        }
        return AlgorithmTypes.createMesAlgorithm(
                transportDataManager,
                cargoDataManager
        );
    }
}
