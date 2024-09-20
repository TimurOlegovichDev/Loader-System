package loader;

import loader.controllers.MainController;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoaderApplication {

    public static void main(String[] args) {
        log.info("Starting application");
        new MainController().start();
        log.info("System shut down");
    }

}
