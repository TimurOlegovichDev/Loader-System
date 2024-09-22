package loader.controllers;

import loader.db.TransportDataManager;
import loader.factories.cargo.DefaultCargoFactory;
import loader.input.UserInputReceiver;
import loader.model.entites.Transport;
import loader.model.enums.Scenarios;
import loader.model.structures.TransportJsonStructure;
import loader.utils.CargoCounter;
import loader.utils.FileHandler;
import loader.utils.initializers.CargoInitializer;
import loader.utils.initializers.TruckInitializer;
import loader.utils.json.JsonService;
import loader.validator.CargoValidator;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class MainController {

    private static final String CARGO_FILE_PROMPT = "Введите путь к файлу для считывания груза: ";
    private static final String TRANSPORT_FILE_PROMPT = "Введите путь к json файлу для добавления транспорта:";
    private static final String SAVE_FILE_PROMPT = "Введите путь к json файлу для сохранения данных: ";
    private static final String GET_TRANSPORT_NUMBER_PROMPT = "Введите количество транспорта, которое хотите создать: ";

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

    public void start() {
        for (Scenarios scenario : Scenarios.values()) {
            executeScenario(scenario);
        }
    }

    private void executeScenario(Scenarios scenario) {
        switch (scenario) {
            case INITIALIZE_ENTITIES -> initEntities();
            case LOAD_CARGOS_INTO_TRANSPORT -> loadCargos();
            case SAVE_DATA -> save();
            case PRINT_DATA -> printTransports();
        }
    }

    private void initEntities() {
        initCargos();
        initTransports();
    }

    private void initCargos() {
        String filepath = userInputReceiver.getInputLine(CARGO_FILE_PROMPT);
        initController.initializeCargos(fileHandler.read(filepath));
    }

    private void initTransports() {
        int num = userInputReceiver.getNumber(GET_TRANSPORT_NUMBER_PROMPT);
        initController.initializeTransport(num);
        initTransportFromFile();
    }

    private void initTransportFromFile() {
        String filepath = userInputReceiver.getInputLine(TRANSPORT_FILE_PROMPT);
        initController.initializeTransport(filepath);
    }

    private void loadCargos() {
        loadingController.startLoading(
                transportationDataContainer.getCargoDataManager(),
                transportationDataContainer.getTransportDataManager()
        );
    }

    private void save() {
        String filepath = userInputReceiver.getInputLine(SAVE_FILE_PROMPT);
        List<TransportJsonStructure> structures = new ArrayList<>();
        TransportDataManager transportDataManager = transportationDataContainer.getTransportDataManager();
        for (Transport transport : transportDataManager.getData()) {
            structures.add(
                    new TransportJsonStructure(
                            transport.getBody(),
                            transportDataManager.getCargos(transport)
                    )
            );
        }
        jsonService.writeObject(structures, filepath);
    }

    private void printTransports() {
        log.info("Отображение транспорта");
        if (!transportationDataContainer.getTransportDataManager().getData().isEmpty()) {
            log.info("{}{}", System.lineSeparator(), transportationDataContainer.getTransportDataManager());
        }
    }
}