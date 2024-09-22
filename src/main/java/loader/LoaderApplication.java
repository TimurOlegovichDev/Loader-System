package loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import loader.controllers.InitController;
import loader.controllers.LoadingController;
import loader.controllers.MainController;
import loader.controllers.impl.DefaultInitController;
import loader.controllers.impl.DefaultLoadingController;
import loader.db.CargoDataManager;
import loader.db.TransportDataManager;
import loader.db.TransportationDataContainer;
import loader.factories.cargo.DefaultCargoFactory;
import loader.input.UserInputReceiver;
import loader.utils.CargoCounter;
import loader.utils.FileHandler;
import loader.utils.initializers.CargoInitializer;
import loader.utils.initializers.TruckInitializer;
import loader.utils.json.JsonReader;
import loader.utils.json.JsonService;
import loader.utils.json.JsonWriter;
import loader.validator.CargoValidator;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;

@Log4j2
public class LoaderApplication {

    public static void main(String[] args) {
        log.info("Программа запущена");
        TransportationDataContainer transportationDataContainer = new TransportationDataContainer(
                new TransportDataManager(
                        new HashMap<>()
                ),
                new CargoDataManager(
                        new ArrayList<>()
                )
        );
        JsonService jsonService = new JsonService(
                new JsonWriter(
                        new ObjectMapper()
                ),
                new JsonReader(
                        new ObjectMapper()
                )
        );
        CargoInitializer cargoInitializer = new CargoInitializer(
                new CargoValidator(),
                new DefaultCargoFactory()
        );
        TruckInitializer truckInitializer = new TruckInitializer(jsonService);
        InitController initController = new DefaultInitController(
                truckInitializer,
                cargoInitializer,
                transportationDataContainer,
                new CargoCounter()
        );
        LoadingController loadingController = new DefaultLoadingController();
        UserInputReceiver userInputReceiver = new UserInputReceiver();
        FileHandler fileHandler = new FileHandler();
        new MainController(
                transportationDataContainer,
                initController,
                loadingController,
                userInputReceiver,
                fileHandler,
                jsonService
        ).start();
        log.info("Завершение работы");
    }
}
