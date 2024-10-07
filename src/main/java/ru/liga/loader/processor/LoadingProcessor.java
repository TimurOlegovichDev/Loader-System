package ru.liga.loader.processor;

import ru.liga.loader.algorithm.LoadingCargoAlgorithm;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;

import java.util.List;

public interface LoadingProcessor {

    void process(String algorithmName);

    void process(String algorithmName,
                 List<Transport> transports,
                 List<Cargo> cargos);

    void process(LoadingCargoAlgorithm algorithm);
}
