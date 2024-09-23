package ru.liga.loader.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.db.TransportDataManager;
import ru.liga.loader.enums.Scenarios;
import ru.liga.loader.input.UserInputReceiver;
import ru.liga.loader.model.entites.Transport;
import ru.liga.loader.model.structures.TransportJsonStructure;
import ru.liga.loader.processor.LoadingProcessor;
import ru.liga.loader.util.FileHandler;
import ru.liga.loader.util.initializers.ComponentInitializer;
import ru.liga.loader.util.json.JsonService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MainController {

    private static final String CARGO_FILE_PROMPT = "Введите путь к файлу для считывания груза: ";
    private static final String TRANSPORT_FILE_PROMPT = "Введите путь к json файлу для добавления транспорта:";
    private static final String SAVE_FILE_PROMPT = "Введите путь к json файлу для сохранения данных: ";
    private static final String GET_TRANSPORT_NUMBER_PROMPT = "Введите количество транспорта, которое хотите создать: ";

    private final TransportDataManager transportDataManager;
    private final ComponentInitializer initController;
    private final LoadingProcessor defaultLoadingProcessor;
    private final UserInputReceiver userInputReceiver;
    private final FileHandler fileHandler;
    private final JsonService jsonService;

    public void start() {
        for (Scenarios scenario : Scenarios.values()) {
            executeScenario(scenario);
        }
    }

    private void executeScenario(Scenarios scenario) {
        switch (scenario) {
            case INITIALIZE_ENTITIES -> initEntities();
            case LOAD_CARGOS_INTO_TRANSPORT -> loadCargos();
            case SAVE_DATA -> save();
            case PRINT_DATA -> printTransports();
        }
    }

    private void initEntities() {
        initCargos();
        initTransports();
    }

    private void initCargos() {
        String filepath = userInputReceiver.getInputLine(CARGO_FILE_PROMPT);
        initController.initializeCargos(fileHandler.read(filepath));
    }

    private void initTransports() {
        int num = userInputReceiver.getNumber(GET_TRANSPORT_NUMBER_PROMPT);
        initController.initializeTransport(num);
        initTransportFromFile();
    }

    private void initTransportFromFile() {
        String filepath = userInputReceiver.getInputLine(TRANSPORT_FILE_PROMPT);
        initController.initializeTransport(filepath);
    }

    private void loadCargos() {
        defaultLoadingProcessor.process(
                userInputReceiver.getInputLine("Выберите алгоритм погрузки " +
                        "( EL - равномерная погрузка, " +
                        "MES - плотная загрузка )")
        );
    }

    private void save() {
        String filepath = userInputReceiver.getInputLine(SAVE_FILE_PROMPT);
        List<TransportJsonStructure> structures = new ArrayList<>();
        for (Transport transport : transportDataManager.getData()) {
            structures.add(
                    new TransportJsonStructure(
                            transport.getBody(),
                            transportDataManager.getCargos(transport)
                    )
            );
        }
        jsonService.writeObject(structures, filepath);
    }

    private void printTransports() {
        log.info("Отображение транспорта");
        if (!transportDataManager.getData().isEmpty()) {
            log.info("{}{}",
                    System.lineSeparator(),
                    transportDataManager
            );
        }
    }
}