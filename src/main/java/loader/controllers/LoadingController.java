package loader.controllers;

import loader.algorithms.LoadingCargoAlgorithm;
import loader.db.CargoDataManager;
import loader.db.TransportDataManager;
import loader.input.UserInputReceiver;
import loader.model.enums.AlgorithmTypes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadingController {

    public void startLoading(CargoDataManager cargoDataManagerSrc, TransportDataManager transportDataManagerDest) {
        log.info("Старт процесса погрузки...");
        LoadingCargoAlgorithm algorithm = getAlgorithm();
        try {
            algorithm.execute(cargoDataManagerSrc, transportDataManagerDest);
        } catch (Exception e) {
            log.error("Процесс погрузки прерван: {}", e.getMessage());
            return;
        }
        log.info("Процесс погрузки успешно завершен.");
    }

    private LoadingCargoAlgorithm getAlgorithm() {
        String algorithmName = new UserInputReceiver()
                .getInputLine(
                        "Введите название алгоритма погрузки " +
                                "(EL - равномерная погрузка," +
                                " MES - плотная погрузка): "
                );
        return AlgorithmTypes.of(algorithmName);
    }

}
