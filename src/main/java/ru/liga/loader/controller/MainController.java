package ru.liga.loader.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.input.UserInputReceiver;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportJsonStructure;
import ru.liga.loader.processor.LoadingProcessor;
import ru.liga.loader.repository.TransportRepository;
import ru.liga.loader.repository.impl.DefaultCrudTransportRepository;
import ru.liga.loader.service.InitializeService;
import ru.liga.loader.service.JsonService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MainController {

    private final TransportRepository transportDataRepository;
    private final InitializeService initController;
    private final LoadingProcessor defaultLoadingProcessor;
    private final JsonService jsonService;

    @Autowired
    public MainController(DefaultCrudTransportRepository transportDataRepository,
                          InitializeService initController,
                          LoadingProcessor defaultLoadingProcessor,
                          UserInputReceiver userInputReceiver,
                          JsonService jsonService) {
        this.transportDataRepository = transportDataRepository;
        this.initController = initController;
        this.defaultLoadingProcessor = defaultLoadingProcessor;
        this.jsonService = jsonService;
    }

    public void initCargos(String filePath) {
        initController.initializeCargos(filePath);
    }

    public void initTransportFromFile(String filePath) {
        initController.initializeTransport(filePath);
    }

    public void loadCargos(String algoName) {
        defaultLoadingProcessor.process(algoName);
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