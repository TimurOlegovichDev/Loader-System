package ru.liga.loadersystem.algorithm;

import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;

import java.util.List;

public interface LoadingCargoAlgorithm {

    void execute(List<Cargo> cargoToLoad, List<Transport> transportToLoad);
}