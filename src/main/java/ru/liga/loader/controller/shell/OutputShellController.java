package ru.liga.loader.controller.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.service.CargoRepositoryService;
import ru.liga.loader.service.TransportRepositoryService;

@ShellComponent
@ShellCommandGroup("Вывод данных")
public class OutputShellController {

    private final TransportRepositoryService transportRepositoryService;
    private final CargoRepositoryService cargoRepositoryService;

    @Autowired
    public OutputShellController(TransportRepositoryService transportRepositoryService, CargoRepositoryService cargoRepositoryService) {
        this.transportRepositoryService = transportRepositoryService;
        this.cargoRepositoryService = cargoRepositoryService;
    }

    @ShellMethod(key = "Информация о транспорте")
    public String getTransportInfo() {
        return transportRepositoryService.getTransportsInfo();
    }

    @ShellMethod(key = "Информация о грузах")
    public String getCargoInfo() {
        return cargoRepositoryService.getCargosInfo();
    }

    @ShellMethod(key = "Информация о транспорте с идентификатором")
    public String getCurrentTransportInfo(String id) {
        return transportRepositoryService.getTransportInfoById(id);
    }

    @ShellMethod(key = "Информация о грузе с названием")
    public String getCurrentCargoInfo(String name) {
        return cargoRepositoryService.getCargoInfoByName(name);
    }

    @ShellMethod(key = "Сохранить данные в файл")
    public void save(String filePath) {
        transportRepositoryService.saveToJson(filePath);
    }
}
