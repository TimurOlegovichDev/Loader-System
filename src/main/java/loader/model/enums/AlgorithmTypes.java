package loader.model.enums;

import loader.algorithms.EvenLoadingAlgorithm;
import loader.algorithms.LoadingCargoAlgorithm;
import loader.algorithms.MinimumEmptySpaceAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum AlgorithmTypes {

    MES(new MinimumEmptySpaceAlgorithm()),
    EL(new EvenLoadingAlgorithm());

    private final LoadingCargoAlgorithm algorithm;


    AlgorithmTypes(LoadingCargoAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public static LoadingCargoAlgorithm of(String algorithmName) {
        try {
            return AlgorithmTypes.valueOf(
                    AlgorithmTypes.class,
                    algorithmName.toUpperCase()
            ).algorithm;
        } catch (IllegalArgumentException e) {
            log.error("Invalid algorithm name, defaulting to MES");
            return new MinimumEmptySpaceAlgorithm();
        }
    }

}
