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

    /**
     * Обрабатывает загрузку грузов по указанному алгоритму.
     * Этот метод получает алгоритм загрузки грузов по указанному имени и выполняет его.
     *
     * @param algorithmName имя алгоритма загрузки грузов
     */

    @Override
    public void process(String algorithmName) {
        load(getAlgorithm(algorithmName));
    }

    /**
     * Загружает грузы в транспортные средства по указанному алгоритму.
     * Этот метод выполняет алгоритм загрузки грузов и загружает грузы в транспортные средства.
     *
     * @param algorithm алгоритм загрузки грузов
     */

    private void load(LoadingCargoAlgorithm algorithm) {
        try {
            algorithm.execute();
        } catch (Exception e) {
            log.error("Процесс погрузки прерван: {}", e.getMessage());
        }
    }

    private LoadingCargoAlgorithm getAlgorithm(String algorithmName) {
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
