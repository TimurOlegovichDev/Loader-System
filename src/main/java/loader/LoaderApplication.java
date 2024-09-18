package loader;

import loader.model.controllers.MainController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoaderApplication {

    private static final Logger logger = LoggerFactory.getLogger(LoaderApplication.class);

    public static void main(String[] args) {
        logger.info("Starting application");
        new MainController().start();
        logger.info("System shut down");
    }
}
