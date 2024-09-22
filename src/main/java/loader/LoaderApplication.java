package loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import loader.controllers.LoadingController;
import loader.controllers.MainController;
import loader.controllers.TransportationCargoContainer;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.input.UserInputReceiver;
import loader.utils.FileHandler;
import loader.utils.json.JsonReader;
import loader.utils.json.JsonService;
import loader.utils.json.JsonWriter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class LoaderApplication {

    public static void main(String[] args) throws IOException {
        log.info("Starting application");
        new MainController(
                new TransportationCargoContainer(
                        new TransportData(),
                        new CargoData()
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
        log.info("System shut down");
    }

}
