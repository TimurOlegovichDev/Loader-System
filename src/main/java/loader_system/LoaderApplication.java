package loader_system;

import loader_system.model.controllers.MainController;

import java.io.IOException;

public class LoaderApplication {
    public static void main(String[] args) throws IOException {
        MainController controller = new MainController();
        controller.start();
    }
}
