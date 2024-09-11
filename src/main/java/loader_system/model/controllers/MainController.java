package loader_system.model.controllers;

import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.algorithms.Algorithm;
import loader_system.model.algorithms.MinimumEmptySpace;
import loader_system.model.factories.cargo.BoxFactory;
import loader_system.model.factories.cargo.CargoFactory;
import loader_system.model.utils.BoxInitializer;
import loader_system.model.utils.Initializer;
import loader_system.ui.in.InputReader;
import loader_system.ui.out.Printer;

import java.io.IOException;

public class MainController {

    private final int WINDOW_WIDTH = 80;

    private final TransportData transportData;

    private final CargoData cargoData;

    private final Printer printer = new Printer(WINDOW_WIDTH);

    public MainController() {
        this.transportData = new TransportData();
        this.cargoData = new CargoData();
    }

    public void start() throws IOException {
        printer.printCentered("Program started!");
        Initializer initializer = new BoxInitializer();
        initializer.initialize(
                new InputReader()
                        .readFile("D:\\JavaWorkSpace\\loader-application\\src\\test\\files\\validData"),
                cargoData);
        Algorithm algorithm = new MinimumEmptySpace();
        algorithm.execute(cargoData, transportData);
        System.out.println(transportData);
    }


}
