package ru.liga.loader.controller.shell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.model.structure.TransportSizeStructure;
import ru.liga.loader.parser.StringParser;
import ru.liga.loader.service.CargoRepositoryService;
import ru.liga.loader.service.InitializeService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
@ShellComponent
@ShellCommandGroup("Добавление данных")
public class InputShellController {

    private final InitializeService initializeService;
    private final StringParser<List<TransportSizeStructure>> stringParser;
    private final CargoRepositoryService cargoRepositoryService;

    @Autowired
    public InputShellController(InitializeService initializeService, StringParser<List<TransportSizeStructure>> stringParser, CargoRepositoryService cargoRepositoryService) {
        this.initializeService = initializeService;
        this.stringParser = stringParser;
        this.cargoRepositoryService = cargoRepositoryService;
    }

    @ShellMethod(key = "Инициализировать груз из файла")
    public void initCargosFromFile(String filePath) throws FileNotFoundException {
        initializeService.initializeCargos(new FileInputStream(filePath));
    }

    @ShellMethod(key = "Инициализировать транспорт из файла")
    public void initTransportFromFile(String filePath) throws FileNotFoundException {
        initializeService.initializeTransport(new FileInputStream(filePath));
    }

    @ShellMethod(key = "Добавить транспорт с указанным размером")
    public void addTransportWithSize(String input) {
        initializeService.initializeTransport(
                stringParser.parse(input)
        );
    }

    @ShellMethod(key = "Добавить посылку")
    public String addCargo(String cargoName, String cargoForm) {
        return cargoRepositoryService.create(cargoName, cargoForm);
    }
}