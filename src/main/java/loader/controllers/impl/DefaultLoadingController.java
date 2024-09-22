package loader.controllers.impl;

import loader.algorithms.LoadingCargoAlgorithm;
import loader.controllers.LoadingController;
import loader.db.CargoDataManager;
import loader.db.TransportDataManager;
import loader.enums.AlgorithmTypes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultLoadingController implements LoadingController {

    @Override
    public void startLoading(CargoDataManager cargoDataManagerSrc,
                             TransportDataManager transportDataManagerDest,
                             String algorithmName) {
        log.info("Старт процесса погрузки...");
        LoadingCargoAlgorithm algorithm = AlgorithmTypes.of(algorithmName);
        try {
            algorithm.execute(cargoDataManagerSrc, transportDataManagerDest);
        } catch (Exception e) {
            log.error("Процесс погрузки прерван: {}", e.getMessage());
            return;
        }
        log.info("Процесс погрузки успешно завершен.");
    }
}
