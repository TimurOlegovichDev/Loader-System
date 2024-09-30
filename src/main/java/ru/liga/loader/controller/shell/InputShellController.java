package ru.liga.loader.controller.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.controller.MainController;

@ShellComponent
@ShellCommandGroup("Добавление сущностей")
public class InputShellController {

    private final MainController controller;

    @Autowired
    public InputShellController(MainController controller) {
        this.controller = controller;
    }

    @ShellMethod(key = "Импортировать груз из файла")
    public void readCargosFromJsonFile(String filePath) {
        controller.initCargos(filePath);
    }

    @ShellMethod(key = "Импортировать транспорт из файла")
    public void readTrucksFromJsonFile(String filePath) {
        controller.initTransportFromFile(filePath);
    }

    @ShellMethod(key = "Добавить транспорт с указаным размером")
    public void addTransportFromInputLine(String input) {
        controller.initTransports(input);
    }
}
