package ru.liga.loadersystem.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.liga.loadersystem.algorithm.EvenLoadingAlgorithm;
import ru.liga.loadersystem.algorithm.LoadingCargoAlgorithm;
import ru.liga.loadersystem.algorithm.MinimumEmptySpaceAlgorithm;
import ru.liga.loadersystem.algorithm.util.impl.DefaultCargoSorter;
import ru.liga.loadersystem.algorithm.util.impl.TransportSorterByOccupiedAreaAsc;
import ru.liga.loadersystem.algorithm.util.impl.TransportSorterByOccupiedAreaDesc;
import ru.liga.loadersystem.repository.CargoCrudRepository;
import ru.liga.loadersystem.repository.TransportCrudRepository;
import ru.liga.loadersystem.service.TransportService;
import ru.liga.loadersystem.util.DefaultCargoLoader;

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

    public static LoadingCargoAlgorithm of(String algorithmName,
                                           CargoCrudRepository cargoCrudRepository,
                                           TransportCrudRepository transportCrudRepository) {
        try {
            return getAlgorithm(
                    AlgorithmTypes.valueOf(algorithmName.toUpperCase()),
                    cargoCrudRepository,
                    transportCrudRepository
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return getAlgorithm(
                    MES,
                    cargoCrudRepository,
                    transportCrudRepository
            );
        }
    }

    private static LoadingCargoAlgorithm getAlgorithm(AlgorithmTypes algo,
                                                      CargoCrudRepository cargoCrudRepository,
                                                      TransportCrudRepository transportCrudRepository) {
        switch (algo) {
            case EL -> {
                return new EvenLoadingAlgorithm(
                        new DefaultCargoSorter(),
                        new TransportSorterByOccupiedAreaAsc(
                                new TransportService(
                                        transportCrudRepository,
                                        cargoCrudRepository
                                )
                        ),
                        transportCrudRepository,
                        new DefaultCargoLoader(),
                        cargoCrudRepository
                );
            }
            default -> {
                return new MinimumEmptySpaceAlgorithm(
                        new DefaultCargoSorter(),
                        new TransportSorterByOccupiedAreaDesc(
                                new TransportService(
                                        transportCrudRepository,
                                        cargoCrudRepository
                                )
                        ),
                        transportCrudRepository,
                        new DefaultCargoLoader(),
                        cargoCrudRepository
                );
            }
        }
    }
}
