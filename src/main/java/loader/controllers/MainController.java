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

    private final TransportationDataContainer transportationDataContainer;
    private final InitController initController;
    private final LoadingController loadingController;
    private final UserInputReceiver userInputReceiver;
    private final FileHandler fileHandler;
    private final JsonService jsonService;

    public MainController(TransportationDataContainer transportationDataContainer,
                          LoadingController loadingController,
                          UserInputReceiver userInputReceiver,
                          FileHandler fileHandler,
                          JsonService jsonService) {
        this.transportationDataContainer = transportationDataContainer;
        this.initController = new InitController(
                new TruckInitializer(jsonService),
                new CargoInitializer(
                        new CargoValidator(),
                        new DefaultCargoFactory()
                ),
                transportationDataContainer,
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
        String filepath = userInputReceiver.getInputLine(
                "Введите путь к файлу для считывания груза: "
        );
        initController.initializeCargos(fileHandler.read(filepath));
    }

    private void initTransports() {
        initTransportFromFile();
        int num = userInputReceiver.getNumber(
                "Введите количество транспорта, которое хотите создать: "
        );
        initController.initializeTransport(num);
    }

    private void initTransportFromFile() {
        String filepath = userInputReceiver.getInputLine(
                "Введите путь к json файлу для добавления транспорта:"
        );
        initController.initializeTransport(filepath);
    }

    private void loadCargos() {
        loadingController.startLoading(
                transportationDataContainer.getCargoDataManager(),
                transportationDataContainer.getTransportDataManager()
        );
    }

    private void save() {
        String filepath = userInputReceiver.getInputLine(
                "Введите путь к json файлу для сохранения данных: "
        );
        jsonService.writeObject(transportationDataContainer.getTransportDataManager(), filepath);
    }

    private void printTransports() {
        log.info("Отображение транспорта");
        if (!transportationDataContainer.getTransportDataManager().getData().isEmpty()) {
            log.info("{}{}",
                    System.lineSeparator(),
                    transportationDataContainer.getTransportDataManager()
            );
        }
    }

}