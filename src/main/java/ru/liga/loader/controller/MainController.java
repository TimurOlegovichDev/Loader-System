package ru.liga.loader.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.enums.AlgorithmTypes;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportJsonStructure;
import ru.liga.loader.processor.LoadingProcessor;
import ru.liga.loader.repository.CargoCrudRepository;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.repository.impl.DefaultCrudTransportRepository;
import ru.liga.loader.service.InitializeService;
import ru.liga.loader.service.JsonService;
import ru.liga.loader.util.parser.CargoParser;
import ru.liga.loader.util.parser.TransportSizeParser;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MainController {

    private final TransportCrudRepository transportDataRepository;
    private final InitializeService initController;
    private final LoadingProcessor defaultLoadingProcessor;
    private final JsonService jsonService;
    private final TransportSizeParser transportSizeParser;
    private final CargoParser cargoParser;
    private final CargoCrudRepository cargoRepository;

    @Autowired
    public MainController(DefaultCrudTransportRepository transportDataRepository,
                          InitializeService initController,
                          LoadingProcessor defaultLoadingProcessor,
                          JsonService jsonService,
                          TransportSizeParser transportSizeParser,
                          CargoParser cargoParser,
                          CargoCrudRepository cargoRepository) {
        this.transportDataRepository = transportDataRepository;
        this.initController = initController;
        this.defaultLoadingProcessor = defaultLoadingProcessor;
        this.jsonService = jsonService;
        this.transportSizeParser = transportSizeParser;
        this.cargoParser = cargoParser;
        this.cargoRepository = cargoRepository;
    }

    public void initCargos(String filePath) {
        initController.initializeCargos(filePath);
    }

    public void initTransports(String inputSizes) {
        initController.initializeTransport(transportSizeParser.parse(inputSizes));
    }

    public void initTransportFromFile(String filePath) {
        initController.initializeTransport(filePath);
    }

    public void loadCargos(String algoName) {
        defaultLoadingProcessor.process(algoName);
    }

    public void loadCargosByName(String input) {
        defaultLoadingProcessor.process(
                AlgorithmTypes.createMesAlgorithm(
                        transportDataRepository,
                        cargoParser.parse(
                                input,
                                cargoRepository
                        )
                )
        );
    }

    public void save(String filePath) {
        List<TransportJsonStructure> structures = new ArrayList<>();
        for (Transport transport : transportDataRepository.getKeys()) {
            structures.add(
                    new TransportJsonStructure(
                            transport.getBody(),
                            transportDataRepository.getCargos(transport)
                    )
            );
        }
        jsonService.writeObject(structures, filePath);
    }

    public String printTransports() {
        StringBuilder stringBuilder = new StringBuilder("Отображение транспорта:");
        if (!transportDataRepository.getKeys().isEmpty()) {
            stringBuilder.append(System.lineSeparator())
                    .append(transportDataRepository);
        }
        return stringBuilder.toString();
    }
}