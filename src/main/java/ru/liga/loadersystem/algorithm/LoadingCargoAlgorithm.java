package ru.liga.loadersystem.algorithm;

import ru.liga.loadersystem.algorithm.util.CargoSorter;
import ru.liga.loadersystem.algorithm.util.TransportSorter;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.repository.TransportCrudRepository;

import java.util.List;

public interface LoadingCargoAlgorithm {

    /**
     * Выполняет определенный алгоритм погрузки.
     * В зависимости от реализации, посылки грузятся в транспорт.
     *
     * @param cargoToLoad     - список посылок для загрузки
     * @param transportToLoad - список транспорта для погрузки
     * @see CargoSorter#sort(List) - сортировщик посылок
     * @see TransportSorter#sort(TransportCrudRepository, List) - сортировщик транспорта
     */
    void execute(List<Cargo> cargoToLoad, List<Transport> transportToLoad);
}