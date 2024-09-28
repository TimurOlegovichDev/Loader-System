package ru.liga.loader.service;

import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;

public interface CargoLoaderService {

    void load(Cargo cargo, Transport transport);
}
