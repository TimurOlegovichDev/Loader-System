package ru.liga.loader.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.algorithm.EvenLoadingAlgorithm;
import ru.liga.loader.algorithm.LoadingCargoAlgorithm;
import ru.liga.loader.algorithm.MinimumEmptySpaceAlgorithm;
import ru.liga.loader.algorithm.util.impl.DefaultCargoLoader;
import ru.liga.loader.algorithm.util.impl.DefaultCargoSorter;
import ru.liga.loader.algorithm.util.impl.TransportSorterByWeightAsc;
import ru.liga.loader.algorithm.util.impl.TransportSorterByWeightDesc;
import ru.liga.loader.db.CargoDataManager;
import ru.liga.loader.db.TransportDataManager;

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
     * @param transportDataManager менеджер данных транспортных средств для получения информации о транспорте для погрузки
     * @param cargoDataManager     менеджер данных грузов, которые нужно будет загрузить
     * @return алгоритм равномерной загрузки грузов
     */

    public static LoadingCargoAlgorithm createElAlgorithm(TransportDataManager transportDataManager,
                                                          CargoDataManager cargoDataManager) {
        return new EvenLoadingAlgorithm(
                new DefaultCargoSorter(),
                new TransportSorterByWeightAsc(),
                transportDataManager,
                cargoDataManager,
                new DefaultCargoLoader()
        );
    }

    /**
     * Создает алгоритм плотной загрузки грузов.
     * Этот метод создает экземпляр алгоритма плотной загрузки грузов с указанными менеджерами данных.
     *
     * @param transportDataManager менеджер данных транспортных средств для получения информации о транспорте для погрузки
     * @param cargoDataManager     менеджер данных грузов, которые нужно будет загрузить
     * @return алгоритм плотной загрузки грузов
     */

    public static LoadingCargoAlgorithm createMesAlgorithm(TransportDataManager transportDataManager,
                                                           CargoDataManager cargoDataManager) {
        return new MinimumEmptySpaceAlgorithm(
                new DefaultCargoSorter(),
                new TransportSorterByWeightDesc(),
                transportDataManager,
                cargoDataManager,
                new DefaultCargoLoader()
        );
    }
}
