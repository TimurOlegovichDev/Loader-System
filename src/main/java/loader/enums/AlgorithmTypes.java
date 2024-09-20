package loader.enums;

import loader.algorithms.EvenLoading;
import loader.algorithms.LoadingCargoAlgorithm;
import loader.algorithms.MinimumEmptySpace;
import lombok.Getter;

@Getter
public enum AlgorithmTypes {

    MES(new MinimumEmptySpace()),
    EL(new EvenLoading());

    private final LoadingCargoAlgorithm algorithm;

    AlgorithmTypes(LoadingCargoAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

}
