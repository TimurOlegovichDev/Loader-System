package loader.enums;

import loader.algorithms.Algorithm;
import loader.algorithms.EvenLoading;
import loader.algorithms.MinimumEmptySpace;
import lombok.Getter;

@Getter
public enum AlgorithmTypes {

    MES(new MinimumEmptySpace()),
    EL(new EvenLoading());

    private final Algorithm algorithm;

    AlgorithmTypes(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
}
