package loader;

import loader.controllers.LoadingController;
import loader.controllers.MainController;
import loader.controllers.Repository;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.input.UserInputReceiver;
import loader.utils.FileHandler;
import loader.utils.json.JsonService;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class LoaderApplication {

    public static void main(String[] args) throws IOException {
        log.info("Starting application");
        new MainController(
                new Repository(
                        new TransportData(),
                        new CargoData()
                ),
                new LoadingController(),
                new UserInputReceiver(),
                new FileHandler(
                        new JsonService()
                )
        ).start();
        log.info("System shut down");
    }

}
