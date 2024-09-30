package ru.liga.loader.processor;

import ru.liga.loader.algorithm.LoadingCargoAlgorithm;

public interface LoadingProcessor {

    void process(String algorithmName);

    void process(LoadingCargoAlgorithm algorithm);
}
