package loader;

import loader.controllers.DataController;
import loader.controllers.InitController;
import loader.controllers.LoadingController;
import loader.controllers.MainController;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.input.UserInputReceiver;
import loader.input.json.JsonService;
import loader.utils.CargoCounter;
import loader.utils.FileHandler;
import loader.utils.initializers.cargo.CargoInitializer;
import loader.utils.initializers.transport.TruckInitializer;
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
