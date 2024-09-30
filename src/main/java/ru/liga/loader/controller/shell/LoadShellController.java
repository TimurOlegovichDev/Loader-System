package ru.liga.loader.controller.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.controller.MainController;


@ShellComponent
@ShellCommandGroup("Погрузка посылок")
public class LoadShellController {

    private final MainController controller;

    @Autowired
    public LoadShellController(MainController controller) {
        this.controller = controller;
    }

    @ShellMethod(key = "Выполнить автоматическую погрузку")
    public void loadCargos(String algoName) {
        controller.loadCargos(algoName);
    }

    @ShellMethod(key = "Загрузить посылки по названию")
    public void loadCargosByName(String input) {
        controller.loadCargosByName(input);
    }
}
