package ru.liga.loader.controller.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.controller.MainController;

@ShellComponent
@ShellCommandGroup("Вывод данных")
public class OutputShellController {

    private final MainController controller;

    @Autowired
    public OutputShellController(MainController controller) {
        this.controller = controller;
    }

    @ShellMethod(key = "Смотреть посылки")
    public String printAllCargos() {
        return controller.printCargos();
    }

    @ShellMethod(key = "Смотреть посылку с именем")
    public String printCargo(String name) {
        return controller.printCargoByName(name);
    }

    @ShellMethod(key = "Смотреть посылки в транспорте с идентификатором")
    public String printCargoByTransportId(String id) {
        return controller.printCargoByTransportId(id);
    }

    @ShellMethod(key = "Вывести в консоль")
    public String print() {
        return controller.printTransports();
    }

    @ShellMethod(key = "Сохранить в файл")
    public void save(String filePath) {
        controller.save(filePath);
    }
}
