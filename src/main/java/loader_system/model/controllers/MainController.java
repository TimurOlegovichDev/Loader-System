package loader_system.model.controllers;

import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.algorithms.Algorithm;
import loader_system.model.algorithms.MinimumEmptySpace;
import loader_system.model.factories.algorithm.AlgorithmFactory;
import loader_system.model.utils.BoxInitializer;
import loader_system.model.utils.Initializer;
import loader_system.ui.in.InputFileReader;
import loader_system.ui.in.UserInputReceiver;
import loader_system.ui.out.Printer;

import java.io.IOException;

public class MainController {

    private final int WINDOW_WIDTH = 50;

    private final TransportData transportData;

    private final CargoData cargoData;

    private final Printer printer = new Printer(WINDOW_WIDTH);
    private final UserInputReceiver userInputReceiver = new UserInputReceiver();

    public MainController() {
        this.transportData = new TransportData();
        this.cargoData = new CargoData();
    }

    public void start() throws IOException {
        System.out.println();
        printer.printCentered("Program started!");
        initComponents();
        startLoading();
        printer.printCentered("Loading is over!\n");
        printer.printCentered(transportData.toString());
    }

    private void initComponents() throws IOException {
        Initializer initializer = new BoxInitializer();
        String filepath = userInputReceiver.getInputLine(printer, "Enter file path: ");
        initializer.initialize(
                new InputFileReader().readFile(filepath),
                cargoData
        );
    }

    private void startLoading() {
        Algorithm algorithm = chooseAndCreateAlgo();
        algorithm.execute(cargoData, transportData);
    }

    private Algorithm chooseAndCreateAlgo() {
        String algorithmName = userInputReceiver
                .getInputLine(
                        printer,
                        "Enter algorithm name (OTO - one to one, MES - minimum empty space): "
                );
        return new AlgorithmFactory().getAlgorithm(algorithmName);
    }

}
