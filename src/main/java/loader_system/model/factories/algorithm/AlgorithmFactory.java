package loader_system.model.factories.algorithm;

import loader_system.model.algorithms.Algorithm;
import loader_system.model.algorithms.MinimumEmptySpace;
import loader_system.model.algorithms.OneToOne;
import loader_system.model.enums.AlgorithmTypes;

public class AlgorithmFactory{

    public Algorithm getAlgorithm(String algorithmName){
        try {
            switch (AlgorithmTypes.valueOf(algorithmName.toUpperCase())){
                case MES: return new MinimumEmptySpace();
                case OTO: return new OneToOne();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid algorithm name, defaulting to MES");
        }
        return new MinimumEmptySpace();
    }

}
