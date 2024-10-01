package ru.liga.loader.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.enums.AlgorithmTypes;
import ru.liga.loader.exception.InvalidCargoInput;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportJsonStructure;
import ru.liga.loader.model.structure.TransportSizeStructure;
import ru.liga.loader.parser.StringParser;
import ru.liga.loader.parser.impl.TransportSizeParser;
import ru.liga.loader.processor.LoadingProcessor;
import ru.liga.loader.repository.CargoCrudRepository;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.repository.impl.DefaultCrudTransportRepository;
import ru.liga.loader.service.InitializeService;
import ru.liga.loader.service.JsonService;
import ru.liga.loader.service.TransportUpdateService;
import ru.liga.loader.util.CargoCounter;
import ru.liga.loader.validator.Validator;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MainController {

    private final TransportCrudRepository transportDataRepository;
    private final InitializeService initController;
    private final LoadingProcessor defaultLoadingProcessor;
    private final JsonService jsonService;
    private final StringParser<List<TransportSizeStructure>> transportSizeParser;
    private final StringParser<List<Cargo>> cargoParser;
    private final CargoCrudRepository cargoRepository;
    private final TransportUpdateService transportUpdateService;
    private final CargoCounter cargoCounter;
    private final StringParser<char[][]> cargoFormParser;
    private final Validator<char[][]> cargoFormValidator;

    @Autowired
    public MainController(DefaultCrudTransportRepository transportDataRepository,
                          InitializeService initController,
                          LoadingProcessor defaultLoadingProcessor,
                          JsonService jsonService,
                          TransportSizeParser transportSizeParser,
                          StringParser<List<Cargo>> cargoParser,
                          CargoCrudRepository cargoRepository, TransportUpdateService transportUpdateService, CargoCounter cargoCounter, StringParser<char[][]> cargoFormParser, Validator<char[][]> cargoValidator) {
        this.transportDataRepository = transportDataRepository;
        this.initController = initController;
        this.defaultLoadingProcessor = defaultLoadingProcessor;
        this.jsonService = jsonService;
        this.transportSizeParser = transportSizeParser;
        this.cargoParser = cargoParser;
        this.cargoRepository = cargoRepository;
        this.transportUpdateService = transportUpdateService;
        this.cargoCounter = cargoCounter;
        this.cargoFormParser = cargoFormParser;
        this.cargoFormValidator = cargoValidator;
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
                        cargoParser.parse(input)
                )
        );
    }

    public void save(String filePath) {
        List<TransportJsonStructure> structures = new ArrayList<>();
        for (Transport transport : transportDataRepository.getKeys()) {
            structures.add(
                    new TransportJsonStructure(
                            transport.getId(),
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

    public String printCargos() {
        StringBuilder stringBuilder = new StringBuilder("Отображение грузов:");
        if (!cargoRepository.getKeys().isEmpty()) {
            stringBuilder.append(System.lineSeparator())
                    .append(cargoRepository);
        }
        return stringBuilder.toString();
    }

    public String printCargoByName(String input) {
        StringBuilder stringBuilder = new StringBuilder("Отображение груза:");
        if (cargoRepository.getKeys().contains(input)) {
            stringBuilder.append(System.lineSeparator())
                    .append(cargoRepository.getBy(input));
        } else {
            stringBuilder.append(System.lineSeparator())
                    .append("Груз с таким именем не найден");
        }
        return stringBuilder.toString();
    }

    public String printCargoByTransportId(String input) {
        List<Cargo> list = new ArrayList<>();
        transportDataRepository.getTransportById(input).ifPresent(
                transport -> list.addAll(transportDataRepository.getCargos(transport)));
        StringBuilder stringBuilder = new StringBuilder("Информация о транспорте").append(System.lineSeparator());
        if (list.isEmpty()) {
            return stringBuilder.append("Транспорт пустой").toString();
        }
        cargoCounter.count(list)
                .forEach((name, count) ->
                        stringBuilder.append("Название груза: ")
                                .append(name)
                                .append(", количество: ")
                                .append(count)
                                .append(System.lineSeparator())
                );
        return stringBuilder.toString();
    }

    public void deleteCargosByName(String input) {
        List<Cargo> cargoToRemove = cargoParser.parse(input);
        if (cargoToRemove.isEmpty()) {
            return;
        }
        List<Cargo> loadedCargo = transportDataRepository.getAllCargos();
        List<Cargo> toLoad = new ArrayList<>();
        for (Cargo cargo : loadedCargo) {
            if (!cargoToRemove.contains(cargoRepository.getBy(cargo.getName()))) {
                toLoad.add(cargo);
            }
        }
        cargoToRemove.forEach(cargo -> cargoRepository.delete(cargo.getName()));
        transportUpdateService.update(toLoad);
    }

    public String addCargo(String name, String form) {
        if (cargoRepository.getBy(name) != null) {
            return "Такой груз уже существует, имя должно быть уникальным";
        }
        char[][] formArray = cargoFormParser.parse(form);
        try {
            cargoFormValidator.validate(formArray);
        } catch (InvalidCargoInput e) {
            log.error(e.getMessage());
        }
        cargoRepository.add(
                new Cargo(
                        name,
                        formArray
                )
        );
        return "Груз успешно добавлен";
    }
}