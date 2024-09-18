package loader.model.controllers;

import loader.db.CargoData;
import loader.db.TransportData;
import loader.model.algorithms.Algorithm;
import loader.model.dto.transport.TruckDto;
import loader.model.entites.cargos.Cargo;
import loader.model.entites.transports.Transport;
import loader.model.factories.algorithm.AlgorithmFactory;
import loader.model.utils.CargoCounter;
import loader.model.utils.initializers.cargo.BoxInitializer;
import loader.model.utils.initializers.cargo.CargoInitializer;
import loader.model.utils.initializers.transport.TransportInitializer;
import loader.model.utils.initializers.transport.TruckInitializer;
import loader.model.utils.json.JsonService;
import loader.ui.in.InputFileReader;
import loader.ui.in.UserInputReceiver;
import loader.ui.out.Printer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Slf4j
public class MainController {

    private final TransportData transportData = new TransportData();
    private final CargoData cargoData = new CargoData();

    private final Printer printer = new Printer();
    private final UserInputReceiver userInputReceiver = new UserInputReceiver();

    private final InputFileReader inputFileReader = new InputFileReader();

    private final CargoInitializer boxInitializer = new BoxInitializer();
    private final TransportInitializer truckInitializer = new TruckInitializer();

    private final CargoCounter cargoCounter = new CargoCounter();

    private final JsonService jsonService = new JsonService();

    public void start() {
        try {
            initComponents();
        } catch (IOException e) {
            log.error(e.getMessage());
            return;
        }
        startLoading();
        if(!transportData.getData().isEmpty()){
            log.info("{}{}", System.lineSeparator(), transportData);
        }
        save();
    }

    private void initComponents() throws IOException {
        log.info("Initializing components...");
        initCargos();
        initTransports();
        log.info("Components initialized.");
    }

    private void initCargos() throws IOException {
        log.debug("Initializing cargos...");
        String filepath = userInputReceiver.getInputLine(printer, "Enter file path: ");
        List<Cargo> cargos = boxInitializer.initializeToList(
                inputFileReader.readFile(filepath)
        );
        cargos.forEach(cargoData::add);
        log.info("Cargos initialized.");
    }

    private void initTransports() {
        log.debug("Initializing transports...");
        initToListTransportFromFile();
        int num = userInputReceiver.getNumber(printer, "Enter number of transports to add: ");
        log.debug("Creating {} transports", num);
        truckInitializer.initializeToList(num).forEach(transportData::add);
        log.info("Transports initialized.");
    }

    private void initToListTransportFromFile() {
        String filepath = userInputReceiver.getInputLine(printer, "Enter filepath to import transport:");
        Map<Transport, List<Cargo>> map = truckInitializer.initializeToMapWithCargoFromFile(filepath);
        transportData.add(map);
        countCargos(map);
    }

    private void countCargos(Map<Transport, List<Cargo>> map) {
        for (Transport transport : map.keySet()) {
            List<Cargo> cargos = map.get(transport);
            cargoCounter.countCargos(cargos).forEach((symbol, count) ->
                log.info("Truck contains {} cargos {} type", count, symbol)
            );
            log.info("{}{}", System.lineSeparator(), transport.toString());
        }
    }

    private void startLoading() {
        log.info("Starting loading process...");
        Algorithm algorithm = chooseAndCreateAlgo();
        try {
            algorithm.execute(cargoData, transportData);
            log.info("Loading process complete.");
        } catch (Exception e) {
            log.error("Loading process interrupted: {}", e.getMessage());
        }
    }

    private Algorithm chooseAndCreateAlgo() {
        log.debug("Choosing algorithm...");
        String algorithmName = userInputReceiver
                .getInputLine(
                        printer,
                        "Enter algorithm name (EL - even loading, MES - minimum empty space): "
                );
        log.debug("Creating algorithm: {}", algorithmName);
        return new AlgorithmFactory().getAlgorithm(algorithmName);
    }

    private void save(){
        String path = userInputReceiver.getInputLine(printer, "Enter file path to save data: ");
        List<TruckDto> truckDtos = new ArrayList<>();
        for(Transport transport : transportData.getData()){
            truckDtos.add(new TruckDto(transport.getBody(), transportData.getBoxes(transport)));
        }
        jsonService.writeObject(truckDtos, path);
    }

}