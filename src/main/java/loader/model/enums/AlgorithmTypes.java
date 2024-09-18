package loader.model.enums;

import loader.model.algorithms.Algorithm;
import loader.model.algorithms.EvenLoading;
import loader.model.algorithms.MinimumEmptySpace;
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
