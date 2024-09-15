package loader_system.model.factories.algorithm;

import loader_system.model.algorithms.Algorithm;
import loader_system.model.algorithms.MinimumEmptySpace;
import loader_system.model.algorithms.EvenLoading;
import loader_system.model.enums.AlgorithmTypes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlgorithmFactory{

    public Algorithm getAlgorithm(String algorithmName){
        try {
            return switch (AlgorithmTypes.valueOf(algorithmName.toUpperCase())) {
                case MES -> new MinimumEmptySpace();
                case EL -> new EvenLoading();
            };
        } catch (IllegalArgumentException e) {
            log.error("Invalid algorithm name, defaulting to MES");
        }
        return new MinimumEmptySpace();
    }

}
