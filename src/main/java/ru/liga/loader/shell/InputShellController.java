package ru.liga.loader.shell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.model.structure.TransportSizeStructure;
import ru.liga.loader.parser.StringParser;
import ru.liga.loader.service.CargoService;
import ru.liga.loader.service.InitializeService;

import java.util.List;

@Slf4j
@ShellComponent
@ShellCommandGroup("Добавление данных")
public class InputShellController {

    private final InitializeService initializeService;
    private final StringParser<List<TransportSizeStructure>> stringParser;
    private final CargoService cargoService;

    @Autowired
    public InputShellController(InitializeService initializeService, StringParser<List<TransportSizeStructure>> stringParser, CargoService cargoService) {
        this.initializeService = initializeService;
        this.stringParser = stringParser;
        this.cargoService = cargoService;
    }

    @ShellMethod(key = "Импортировать груз из файла")
    public void importCargosFromFile(String filePath) {
        initializeService.initializeCargos(filePath);
    }

    @ShellMethod(key = "Импортировать транспорт из файла")
    public void importTransportFromFile(String filePath) {
        initializeService.initializeTransport(filePath);
    }

    @ShellMethod(key = "Добавить транспорт с указанным размером")
    public void addTransportWithSize(String input) {
        initializeService.initializeTransport(
                stringParser.parse(input)
        );
    }

    @ShellMethod(key = "Добавить посылку")
    public String addCargo(String cargoName, String cargoForm) {
        return cargoService.create(cargoName, cargoForm);
    }
}