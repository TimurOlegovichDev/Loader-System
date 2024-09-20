package loader;

import loader.controllers.DataController;
import loader.controllers.MainController;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.input.UserInputReceiver;
import loader.utils.json.JsonService;
import loader.utils.FileHandler;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class LoaderApplication {

    public static void main(String[] args) throws IOException {
        log.info("Starting application");
        new MainController(
                        new DataController(
                                new TransportData(),
                                new CargoData()
                        ),
                        new UserInputReceiver(),
                        new FileHandler(
                                new JsonService()
                        )
                ).start();
        log.info("System shut down");
    }

}
