package loader.model.factories.algorithm;

import loader.model.algorithms.Algorithm;
import loader.model.algorithms.MinimumEmptySpace;
import loader.model.algorithms.EvenLoading;
import loader.model.enums.AlgorithmTypes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlgorithmFactory{

    public Algorithm getAlgorithm(String algorithmName){
        try {
            return AlgorithmTypes.valueOf(AlgorithmTypes.class, algorithmName.toUpperCase()).getAlgorithm();
        } catch (IllegalArgumentException e) {
            log.error("Invalid algorithm name, defaulting to MES");
        }
        return new MinimumEmptySpace();
    }

}
