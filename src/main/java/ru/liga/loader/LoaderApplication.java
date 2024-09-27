package ru.liga.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

import java.util.HashMap;

@Slf4j
public class LoaderApplication {

    /**
     * Создает экземляр главного контроллера с необходимыми зависимостями и запускает его
     */

    public static void main(String[] args) {
        log.info("Программа запущена");
        MainController mainController = createMainController();
        mainController.start();
        log.info("Завершение работы");
    }

    private static MainController createMainController() {
        JsonService jsonService = createJsonService();
        TransportDataManager transportDataManager = new TransportDataManager(new HashMap<>());
        TruckInitializer truckInitializer = new TruckInitializer(createJsonService());
        CargoInitializer cargoInitializer = new CargoInitializer(
                new CargoValidator(),
                new DefaultCargoFactory(),
                jsonService
        );
        CargoDataManager cargoDataManager = new CargoDataManager(new HashMap<>());
        CargoCounter cargoCounter = new CargoCounter();
        ComponentInitializer initController = new ComponentInitializer(
                truckInitializer,
                cargoInitializer,
                transportDataManager,
                cargoDataManager,
                cargoCounter
        );
        LoadingProcessor loadingProcessor = new DefaultLoadingProcessor(transportDataManager, cargoDataManager);
        UserInputReceiver userInputReceiver = new UserInputReceiver();
        FileHandler fileHandler = new FileHandler();
        return MainController.builder()
                .transportDataManager(transportDataManager)
                .initController(initController)
                .defaultLoadingProcessor(loadingProcessor)
                .userInputReceiver(userInputReceiver)
                .fileHandler(fileHandler)
                .jsonService(jsonService)
                .build();
    }

    private static JsonService createJsonService() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonWriter jsonWriter = new JsonWriter(objectMapper);
        JsonReader jsonReader = new JsonReader(objectMapper);
        return new JsonService(jsonWriter, jsonReader);
    }
}