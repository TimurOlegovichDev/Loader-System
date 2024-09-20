package loader.controllers;

import loader.algorithms.LoadingCargoAlgorithm;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.input.UserInputReceiver;
import loader.model.enums.AlgorithmTypes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadingController {

    public void startLoading(CargoData cargoDataSrc, TransportData transportDataDest) {
        log.info("Start loading process...");
        LoadingCargoAlgorithm algorithm = getAlgorithm();
        try {
            algorithm.execute(cargoDataSrc, transportDataDest);
        } catch (Exception e) {
            log.error("Loading process interrupted: {}", e.getMessage());
            return;
        }
        log.info("Loading process finished.");
    }

    private LoadingCargoAlgorithm getAlgorithm() {
        String algorithmName = new UserInputReceiver()
                .getInputLine(
                        "Enter algorithm name " +
                                "(EL - even loading," +
                                " MES - minimum empty space): "
                );
        return AlgorithmTypes.of(algorithmName);
    }

}
