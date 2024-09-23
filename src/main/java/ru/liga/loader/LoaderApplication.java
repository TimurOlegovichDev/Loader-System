package ru.liga.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import ru.liga.loader.controller.MainController;
import ru.liga.loader.db.CargoDataManager;
import ru.liga.loader.db.TransportDataManager;
import ru.liga.loader.factory.cargo.DefaultCargoFactory;
import ru.liga.loader.input.UserInputReceiver;
import ru.liga.loader.processor.LoadingProcessor;
import ru.liga.loader.processor.impl.DefaultLoadingProcessor;
import ru.liga.loader.util.CargoCounter;
import ru.liga.loader.util.FileHandler;
import ru.liga.loader.util.initializers.CargoInitializer;
import ru.liga.loader.util.initializers.ComponentInitializer;
import ru.liga.loader.util.initializers.TruckInitializer;
import ru.liga.loader.util.json.JsonReader;
import ru.liga.loader.util.json.JsonService;
import ru.liga.loader.util.json.JsonWriter;
import ru.liga.loader.validator.CargoValidator;

import java.util.ArrayList;
import java.util.HashMap;

@Log4j2
public class LoaderApplication {

    public static void main(String[] args) {
        log.info("Программа запущена");
        MainController mainController = createMainController();
        mainController.start();
        log.info("Завершение работы");
    }

    private static MainController createMainController() {
        TransportDataManager transportDataManager = createTransportDataManager();
        TruckInitializer truckInitializer = createTruckInitializer();
        CargoInitializer cargoInitializer = createCargoInitializer();
        CargoDataManager cargoDataManager = createCargoDataManager();
        CargoCounter cargoCounter = createCargoCounter();
        ComponentInitializer initController = createComponentInitializer(truckInitializer, cargoInitializer, transportDataManager, cargoDataManager, cargoCounter);
        LoadingProcessor loadingProcessor = createLoadingProcessor(transportDataManager, cargoDataManager);
        UserInputReceiver userInputReceiver = createUserInputReceiver();
        FileHandler fileHandler = createFileHandler();
        JsonService jsonService = createJsonService();
        return new MainController(transportDataManager, initController, loadingProcessor, userInputReceiver, fileHandler, jsonService);
    }

    private static TransportDataManager createTransportDataManager() {
        return new TransportDataManager(new HashMap<>());
    }

    private static TruckInitializer createTruckInitializer() {
        return new TruckInitializer(createJsonService());
    }

    private static CargoInitializer createCargoInitializer() {
        return new CargoInitializer(new CargoValidator(), new DefaultCargoFactory());
    }

    private static CargoDataManager createCargoDataManager() {
        return new CargoDataManager(new ArrayList<>());
    }

    private static CargoCounter createCargoCounter() {
        return new CargoCounter();
    }

    private static ComponentInitializer createComponentInitializer(TruckInitializer truckInitializer, CargoInitializer cargoInitializer, TransportDataManager transportDataManager, CargoDataManager cargoDataManager, CargoCounter cargoCounter) {
        return new ComponentInitializer(truckInitializer, cargoInitializer, transportDataManager, cargoDataManager, cargoCounter);
    }

    private static LoadingProcessor createLoadingProcessor(TransportDataManager transportDataManager, CargoDataManager cargoDataManager) {
        return new DefaultLoadingProcessor(transportDataManager, cargoDataManager);
    }

    private static UserInputReceiver createUserInputReceiver() {
        return new UserInputReceiver();
    }

    private static FileHandler createFileHandler() {
        return new FileHandler();
    }

    private static JsonService createJsonService() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonWriter jsonWriter = new JsonWriter(objectMapper);
        JsonReader jsonReader = new JsonReader(objectMapper);
        return new JsonService(jsonWriter, jsonReader);
    }
}