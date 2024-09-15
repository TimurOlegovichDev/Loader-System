package loader_system;

import loader_system.model.controllers.MainController;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoaderApplication {

    private static final Logger logger = LoggerFactory.getLogger(LoaderApplication.class);

    public static void main(String[] args) {
        logger.info("Starting application");
        MainController controller = new MainController();
        try {
            logger.debug("Controller launch");
            controller.start();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
