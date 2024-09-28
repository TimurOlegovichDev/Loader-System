package ru.liga.loader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShellController {

    private final MainController controller;

    @Autowired
    public ShellController(MainController controller) {
        this.controller = controller;
    }

    @ShellMethod(key = "Открыть груз из файла")
    public void readCargosFromJsonFile(String filePath) {
        controller.initCargos(filePath);
    }

    @ShellMethod(key = "Открыть транспорт из файла")
    public void readTrucksFromJsonFile(String filePath) {
        controller.initTransportFromFile(filePath);
    }

    @ShellMethod(key = "Загрузить посылки")
    public void loadCargos(String algoName) {
        controller.loadCargos(algoName);
    }

    @ShellMethod(key = "Вывести данные в консоль")
    public String print() {
        return controller.printTransports();
    }

    @ShellMethod(key = "Сохранить данные в файл")
    public void save(String filePath) {
        controller.save(filePath);
    }

    @ShellMethod(key = "Завершить работу")
    public void shutDown() {
        System.exit(0);
    }
}
