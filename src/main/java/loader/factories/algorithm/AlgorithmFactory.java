package loader.factories.algorithm;

import loader.algorithms.LoadingCargoAlgorithm;
import loader.algorithms.MinimumEmptySpace;
import loader.enums.AlgorithmTypes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlgorithmFactory{

    public LoadingCargoAlgorithm getAlgorithm(String algorithmName){
        try {
            return AlgorithmTypes.valueOf(AlgorithmTypes.class, algorithmName.toUpperCase()).getAlgorithm();
        } catch (IllegalArgumentException e) {
            log.error("Invalid algorithm name, defaulting to MES");
        }
        return new MinimumEmptySpace();
    }

}
