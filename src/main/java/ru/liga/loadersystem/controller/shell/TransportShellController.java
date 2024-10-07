package ru.liga.loadersystem.controller.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loadersystem.model.structure.TransportSizeStructure;
import ru.liga.loadersystem.parser.StringParser;
import ru.liga.loadersystem.service.CargoRepositoryService;
import ru.liga.loadersystem.service.InitializeService;
import ru.liga.loadersystem.service.TransportRepositoryService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@ShellComponent
@ShellCommandGroup("Управление транспортом")
public class TransportShellController {

    private final TransportRepositoryService transportRepositoryService;
    private final CargoRepositoryService cargoRepositoryService;
    private final StringParser<List<TransportSizeStructure>> stringParser;
    private final InitializeService initializeService;

    @Autowired
    public TransportShellController(TransportRepositoryService transportRepositoryService,
                                    CargoRepositoryService cargoRepositoryService,
                                    StringParser<List<TransportSizeStructure>> stringParser, InitializeService initializeService) {
        this.transportRepositoryService = transportRepositoryService;
        this.cargoRepositoryService = cargoRepositoryService;
        this.stringParser = stringParser;
        this.initializeService = initializeService;
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

    @ShellMethod(key = "Удалить груз из системы")
    public String deleteCargoFormSystem(String name, String algoName) {
        return cargoRepositoryService.delete(name, algoName);
    }

    @ShellMethod(key = "Удалить транспорт из системы")
    public String deleteTransportFormSystem(String id) {
        return transportRepositoryService.delete(UUID.fromString(id));
    }
}
