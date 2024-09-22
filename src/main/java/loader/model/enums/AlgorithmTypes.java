package loader.model.enums;

import loader.algorithms.EvenLoadingAlgorithm;
import loader.algorithms.LoadingCargoAlgorithm;
import loader.algorithms.MinimumEmptySpaceAlgorithm;
import loader.algorithms.utils.impl.DefaultCargoLoader;
import loader.algorithms.utils.impl.DefaultCargoSorter;
import loader.algorithms.utils.impl.DefaultTransportValidator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum AlgorithmTypes {

    MES(new MinimumEmptySpaceAlgorithm(
            new DefaultCargoLoader(
                    new DefaultTransportValidator()
            ),
            new DefaultCargoSorter(),
            new DefaultTransportValidator()
    )),
    EL(new EvenLoadingAlgorithm(
            new DefaultCargoLoader(
                    new DefaultTransportValidator()
            ),
            new DefaultCargoSorter(),
            new DefaultTransportValidator()
    ));

    @Getter
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
            return MES.algorithm;
        }
    }

}
