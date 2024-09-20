package loader.controllers;

import loader.algorithms.LoadingCargoAlgorithm;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.factories.algorithm.AlgorithmFactory;
import loader.input.UserInputReceiver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadingController {

    private final UserInputReceiver userInputReceiver;

    public LoadingController(UserInputReceiver userInputReceiver) {
        this.userInputReceiver = userInputReceiver;
    }

    public void startLoading(CargoData cargoData, TransportData transportData){
        log.info("Start loading process...");
        LoadingCargoAlgorithm algorithm = getAlgorithm();
        try {
            algorithm.execute(cargoData, transportData);
        } catch (Exception e) {
            log.error("Loading process interrupted: {}", e.getMessage());
            return;
        }
        log.info("Loading process finished.");
    }

    private LoadingCargoAlgorithm getAlgorithm(){
        String algorithmName = userInputReceiver
                .getInputLine(
                        "Enter algorithm name " +
                                "(EL - even loading," +
                                " MES - minimum empty space): "
                );
        return new AlgorithmFactory().getAlgorithm(algorithmName);
    }

}
