package loader_system;

import loader_system.model.controllers.MainController;

import java.io.IOException;

public class LoaderApplication {
    public static void main(String[] args) {
        loader-implementation
        MainController controller = new MainController();
        try {
            controller.start();
        } catch (IOException e) {
            System.out.println("Cannot find this file");
        }
    }
}
