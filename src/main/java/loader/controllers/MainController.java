package loader.controllers;
import loader.algorithms.LoadingCargoAlgorithm;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.dto.TruckDto;
import loader.entites.cargos.Cargo;
import loader.entites.transports.Transport;
import loader.factories.algorithm.AlgorithmFactory;
import loader.utils.CargoCounter;
import loader.utils.initializers.cargo.CargoInitializer;
import loader.utils.initializers.transport.TransportInitializer;
import loader.utils.initializers.transport.TruckInitializer;
import loader.utils.json.JsonService;
import loader.input.InputFileReader;
import loader.input.UserInputReceiver;
import loader.output.Printer;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Log4j2
public class MainController {

    private final TransportData transportData = new TransportData();
    private final CargoData cargoData = new CargoData();

    private final Printer printer = new Printer();
    private final UserInputReceiver userInputReceiver = new UserInputReceiver();

    private final InputFileReader inputFileReader = new InputFileReader();

    private final CargoInitializer boxInitializer = new CargoInitializer();
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
        List<Cargo> cargos = boxInitializer.initializeCargoToList(
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
        LoadingCargoAlgorithm algorithm = chooseAndCreateAlgo();
        try {
            algorithm.execute(cargoData, transportData);
            log.info("Loading process complete.");
        } catch (Exception e) {
            log.error("Loading process interrupted: {}", e.getMessage());
        }
    }

    private LoadingCargoAlgorithm chooseAndCreateAlgo() {
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
            truckDtos.add(new TruckDto(transport.getBody(), transportData.getCargos(transport)));
        }
        jsonService.writeObject(truckDtos, path);
    }

}