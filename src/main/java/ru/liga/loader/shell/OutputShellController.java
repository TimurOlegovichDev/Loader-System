package ru.liga.loader.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.service.CargoRepositoryService;
import ru.liga.loader.service.TransportService;

@ShellComponent
@ShellCommandGroup("Вывод данных")
public class OutputShellController {

    private final TransportService transportService;
    private final CargoRepositoryService cargoRepositoryService;

    @Autowired
    public OutputShellController(TransportService transportService, CargoRepositoryService cargoRepositoryService) {
        this.transportService = transportService;
        this.cargoRepositoryService = cargoRepositoryService;
    }

    @ShellMethod(key = "Информация о транспорте")
    public String getTransportInfo() {
        return transportService.getTransportsInfo();
    }

    @ShellMethod(key = "Информация о грузах")
    public String getCargoInfo() {
        return cargoRepositoryService.getCargosInfo();
    }

    @ShellMethod(key = "Информация о транспорте с идентификатором")
    public String getCurrentTransportInfo(String id) {
        return transportService.getTransportInfoById(id);
    }

    @ShellMethod(key = "Информация о грузе с названием")
    public String getCurrentCargoInfo(String name) {
        return cargoRepositoryService.getCargoInfoByName(name);
    }

    @ShellMethod(key = "Сохранить данные в файл")
    public void save(String filePath) {
        transportService.saveToJson(filePath);
    }
}
