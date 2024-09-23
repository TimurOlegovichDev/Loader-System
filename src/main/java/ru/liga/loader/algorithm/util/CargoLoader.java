package ru.liga.loader.algorithm.util;

import ru.liga.loader.model.entites.Cargo;
import ru.liga.loader.model.entites.Transport;

public interface CargoLoader {

    void load(Cargo cargo, Transport transport);
}
