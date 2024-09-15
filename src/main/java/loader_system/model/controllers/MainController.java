package loader_system.model.controllers;

import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.algorithms.Algorithm;
import loader_system.model.factories.algorithm.AlgorithmFactory;
import loader_system.model.utils.BoxInitializer;
import loader_system.model.utils.TruckInitializer;
import loader_system.ui.in.InputFileReader;
import loader_system.ui.in.UserInputReceiver;
import loader_system.ui.out.Printer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
public class MainController {

    private final TransportData transportData;

    private final CargoData cargoData;

    private final Printer printer = new Printer();
    private final UserInputReceiver userInputReceiver = new UserInputReceiver();

    public MainController() {
        this.transportData = new TransportData();
        this.cargoData = new CargoData();
    }

    public void start() throws IOException {
        initComponents();
        startLoading();
        if(!transportData.getData().isEmpty()){
            log.info("\n{}", transportData);
        }
    }

    private void initComponents() throws IOException {
        log.info("Initializing components...");
        initCargos();
        initTransports();
        log.info("Components initialized successfully.");
    }

    private void initCargos() throws IOException {
        log.debug("Initializing cargos...");
        String filepath = userInputReceiver.getInputLine(printer, "Enter file path: ");
        new BoxInitializer().initialize(
                new InputFileReader().readFile(filepath),
                cargoData
        );
        log.info("Cargos initialized successfully.");
    }

    private void initTransports() {
        log.debug("Initializing transports...");
        int num = userInputReceiver.getNumber(printer, "Enter number of transports: ");
        log.debug("Creating {} transports", num);
        TruckInitializer truckInitializer = new TruckInitializer();
        truckInitializer.initialize(num, transportData);
        log.info("Transports initialized successfully.");
    }

    private void startLoading() {
        log.info("Starting loading process...");
        Algorithm algorithm = chooseAndCreateAlgo();
        try {
            algorithm.execute(cargoData, transportData);
            log.info("Loading process complete.");
        } catch (Exception e) {
            log.error("Loading process interrupted: {}", e.getMessage());
        }
    }

    private Algorithm chooseAndCreateAlgo() {
        log.debug("Choosing algorithm...");
        String algorithmName = userInputReceiver
                .getInputLine(
                        printer,
                        "Enter algorithm name (EL - even loading, MES - minimum empty space): "
                );
        log.debug("Creating algorithm: {}", algorithmName);
        return new AlgorithmFactory().getAlgorithm(algorithmName);
    }

}