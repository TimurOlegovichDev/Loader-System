package ru.liga.loader.algorithm.util;

import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;

public interface CargoLoader {

    void load(Cargo cargo, Transport transport);
}
