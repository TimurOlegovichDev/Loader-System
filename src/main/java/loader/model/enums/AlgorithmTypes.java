package loader.model.enums;

import loader.algorithms.EvenLoadingAlgorithm;
import loader.algorithms.LoadingCargoAlgorithm;
import loader.algorithms.MinimumEmptySpaceAlgorithm;
import lombok.Getter;

@Getter
public enum AlgorithmTypes {

    MES(new MinimumEmptySpaceAlgorithm()),
    EL(new EvenLoadingAlgorithm());

    private final LoadingCargoAlgorithm algorithm;

    AlgorithmTypes(LoadingCargoAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

}
