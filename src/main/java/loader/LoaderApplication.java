package loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import loader.controllers.LoadingController;
import loader.controllers.MainController;
import loader.controllers.TransportationDataContainer;
import loader.db.CargoDataManager;
import loader.db.TransportDataManager;
import loader.input.UserInputReceiver;
import loader.utils.FileHandler;
import loader.utils.json.JsonReader;
import loader.utils.json.JsonService;
import loader.utils.json.JsonWriter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;

@Log4j2
public class LoaderApplication {

    public static void main(String[] args) {
        log.info("Программа запущена");
        new MainController(
                new TransportationDataContainer(
                        new TransportDataManager(
                                new HashMap<>()
                        ),
                        new CargoDataManager(
                                new ArrayList<>()
                        )
                ),
                new LoadingController(),
                new UserInputReceiver(),
                new FileHandler(),
                new JsonService(
                        new JsonWriter(
                                new ObjectMapper()
                        ),
                        new JsonReader(
                                new ObjectMapper()
                        )
                )
        ).start();
        log.info("Завершение работы");
    }
}
