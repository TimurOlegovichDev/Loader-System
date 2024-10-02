package ru.liga.loader.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.algorithm.EvenLoadingAlgorithm;
import ru.liga.loader.algorithm.LoadingCargoAlgorithm;
import ru.liga.loader.algorithm.MinimumEmptySpaceAlgorithm;
import ru.liga.loader.algorithm.util.impl.DefaultCargoSorter;
import ru.liga.loader.algorithm.util.impl.TransportSorterByWeightAsc;
import ru.liga.loader.algorithm.util.impl.TransportSorterByWeightDesc;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.util.DefaultCargoLoader;

import java.util.List;

@Getter
@Slf4j
public enum AlgorithmTypes {

    MES,
    EL;

    /**
     * Возвращает тип алгоритма загрузки грузов по его имени.
     * Если указанное имя алгоритма не найдено, то возвращается тип MES.
     *
     * @param algorithmName имя алгоритма загрузки грузов
     * @return тип алгоритма загрузки грузов
     */

    public static AlgorithmTypes of(String algorithmName) {
        try {
            return AlgorithmTypes.valueOf(algorithmName.toUpperCase());
        } catch (Exception e) {
            log.error(e.getMessage());
            return MES;
        }
    }

    /**
     * Создает алгоритм равномерной загрузки грузов.
     * Этот метод создает экземпляр алгоритма равномерной загрузки грузов с указанными менеджерами данных.
     *
     * @param transportDataRepository менеджер данных транспортных средств для получения информации о транспорте для погрузки
     * @param cargos                  список грузов, которые нужно будет загрузить
     * @return алгоритм равномерной загрузки грузов
     */

    public static LoadingCargoAlgorithm createElAlgorithm(TransportCrudRepository transportDataRepository,
                                                          List<Transport> transports,
                                                          List<Cargo> cargos) {
        return new EvenLoadingAlgorithm(
                new DefaultCargoSorter(),
                new TransportSorterByWeightAsc(),
                transportDataRepository,
                transports,
                cargos,
                new DefaultCargoLoader()
        );
    }

    /**
     * Создает алгоритм плотной загрузки грузов.
     * Этот метод создает экземпляр алгоритма плотной загрузки грузов с указанными менеджерами данных.
     *
     * @param transportDataRepository менеджер данных транспортных средств для получения информации о транспорте для погрузки
     * @param cargos                  список грузов, которые нужно будет загрузить
     * @return алгоритм плотной загрузки грузов
     */

    public static LoadingCargoAlgorithm createMesAlgorithm(TransportCrudRepository transportDataRepository,
                                                           List<Transport> transports,
                                                           List<Cargo> cargos) {
        return new MinimumEmptySpaceAlgorithm(
                new DefaultCargoSorter(),
                new TransportSorterByWeightDesc(),
                transportDataRepository,
                transports,
                cargos,
                new DefaultCargoLoader()
        );
    }
}
