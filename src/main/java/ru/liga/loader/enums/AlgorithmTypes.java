package ru.liga.loader.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.algorithm.EvenLoadingAlgorithm;
import ru.liga.loader.algorithm.LoadingCargoAlgorithm;
import ru.liga.loader.algorithm.MinimumEmptySpaceAlgorithm;
import ru.liga.loader.algorithm.util.impl.DefaultCargoLoader;
import ru.liga.loader.algorithm.util.impl.DefaultCargoSorter;
import ru.liga.loader.algorithm.util.impl.TransportSorterByWeightAsc;
import ru.liga.loader.algorithm.util.impl.TransportSorterByWeightDesc;
import ru.liga.loader.db.CargoDataManager;
import ru.liga.loader.db.TransportDataManager;

@Getter
@Slf4j
public enum AlgorithmTypes {

    MES,
    EL;

    public static AlgorithmTypes of(String algorithmName) {
        try {
            return AlgorithmTypes.valueOf(algorithmName.toUpperCase());
        } catch (Exception e) {
            log.error(e.getMessage());
            return MES;
        }
    }

    public static LoadingCargoAlgorithm createElAlgorithm(TransportDataManager transportDataManager,
                                                          CargoDataManager cargoDataManager) {
        return new EvenLoadingAlgorithm(
                new DefaultCargoSorter(),
                new TransportSorterByWeightAsc(),
                transportDataManager,
                cargoDataManager,
                new DefaultCargoLoader()
        );
    }

    public static LoadingCargoAlgorithm createMesAlgorithm(TransportDataManager transportDataManager,
                                                           CargoDataManager cargoDataManager) {
        return new MinimumEmptySpaceAlgorithm(
                new DefaultCargoSorter(),
                new TransportSorterByWeightDesc(),
                transportDataManager,
                cargoDataManager,
                new DefaultCargoLoader()
        );
    }
}
