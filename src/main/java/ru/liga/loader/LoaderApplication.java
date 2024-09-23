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
        TransportDataManager transportDataManager = new TransportDataManager(new HashMap<>());
        TruckInitializer truckInitializer = new TruckInitializer(createJsonService());
        CargoInitializer cargoInitializer = new CargoInitializer(
                new CargoValidator(),
                new DefaultCargoFactory()
        );
        CargoDataManager cargoDataManager = new CargoDataManager(new ArrayList<>());
        CargoCounter cargoCounter = new CargoCounter();
        ComponentInitializer initController = createComponentInitializer(
                truckInitializer,
                cargoInitializer,
                transportDataManager,
                cargoDataManager,
                cargoCounter
        );
        LoadingProcessor loadingProcessor = createLoadingProcessor(transportDataManager, cargoDataManager);
        UserInputReceiver userInputReceiver = new UserInputReceiver();
        FileHandler fileHandler = new FileHandler();
        JsonService jsonService = createJsonService();
        return new MainController(transportDataManager, initController, loadingProcessor, userInputReceiver, fileHandler, jsonService);
    }

    private static ComponentInitializer createComponentInitializer(TruckInitializer truckInitializer, CargoInitializer cargoInitializer, TransportDataManager transportDataManager, CargoDataManager cargoDataManager, CargoCounter cargoCounter) {
        return new ComponentInitializer(truckInitializer, cargoInitializer, transportDataManager, cargoDataManager, cargoCounter);
    }

    private static LoadingProcessor createLoadingProcessor(TransportDataManager transportDataManager, CargoDataManager cargoDataManager) {
        return new DefaultLoadingProcessor(transportDataManager, cargoDataManager);
    }

    private static JsonService createJsonService() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonWriter jsonWriter = new JsonWriter(objectMapper);
        JsonReader jsonReader = new JsonReader(objectMapper);
        return new JsonService(jsonWriter, jsonReader);
    }
}