package ru.liga.loadersystem.processor;

import ru.liga.loadersystem.algorithm.LoadingCargoAlgorithm;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;

import java.util.List;

public interface LoadingProcessor {

    void process(LoadingCargoAlgorithm algorithm,
                 List<Transport> transports,
                 List<Cargo> cargos);
}
