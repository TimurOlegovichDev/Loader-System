package loader.controllers;

import loader.factories.cargo.DefaultCargoFactory;
import loader.input.UserInputReceiver;
import loader.model.enums.Scenarios;
import loader.utils.CargoCounter;
import loader.utils.FileHandler;
import loader.utils.initializers.CargoInitializer;
import loader.utils.initializers.TruckInitializer;
import loader.utils.json.JsonService;
import loader.validator.CargoValidator;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class MainController {

    private final TransportationCargoContainer transportationCargoContainer;
    private final InitController initController;
    private final LoadingController loadingController;
    private final UserInputReceiver userInputReceiver;
    private final FileHandler fileHandler;
    private final JsonService jsonService;

    public MainController(TransportationCargoContainer transportationCargoContainer,
                          LoadingController loadingController,
                          UserInputReceiver userInputReceiver,
                          FileHandler fileHandler,
                          JsonService jsonService) {
        this.transportationCargoContainer = transportationCargoContainer;
        this.initController = new InitController(
                new TruckInitializer(jsonService),
                new CargoInitializer(
                        new CargoValidator(),
                        new DefaultCargoFactory()
                ),
                transportationCargoContainer,
                new CargoCounter()
        );
        this.loadingController = loadingController;
        this.userInputReceiver = userInputReceiver;
        this.fileHandler = fileHandler;
        this.jsonService = jsonService;
    }

    public void start() throws IOException {
        for (Scenarios scenario : Scenarios.values()) {
            switch (scenario) {
                case INITIALIZE_ENTITIES -> initEntities();
                case LOAD_CARGOS_INTO_TRANSPORT -> loadCargos();
                case SAVE_DATA -> save();
                case PRINT_DATA -> printTransports();
            }
        }
    }

    private void initEntities() throws IOException {
        initCargos();
        initTransports();
    }

    private void initCargos() {
        String filepath = userInputReceiver.getInputLine("Enter file path: ");
        initController.initializeCargos(fileHandler.read(filepath));
    }

    private void initTransports() {
        initTransportFromFile();
        int num = userInputReceiver.getNumber("Enter number of transports to add: ");
        initController.initializeTransport(num);
    }

    private void initTransportFromFile() {
        String filepath = userInputReceiver.getInputLine("Enter filepath to import transport:");
        initController.initializeTransport(filepath);
    }

    private void loadCargos() {
        loadingController.startLoading(
                transportationCargoContainer.getCargoData(),
                transportationCargoContainer.getTransportData()
        );
    }

    private void save() {
        String filepath = userInputReceiver.getInputLine("Enter file path to save data: ");
        jsonService.writeObject(transportationCargoContainer.getTransportData(), filepath);
    }

    private void printTransports() {
        if (!transportationCargoContainer.getTransportData().getData().isEmpty()) {
            log.info("{}{}",
                    System.lineSeparator(),
                    transportationCargoContainer.getTransportData()
            );
        }
    }

}