package ru.liga.loadersystem.util;

import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;

public interface CargoLoader {

    void load(Cargo cargo, Transport transport);
}
