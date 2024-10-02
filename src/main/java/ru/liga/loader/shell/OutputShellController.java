package ru.liga.loader.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.service.CargoService;
import ru.liga.loader.service.TransportService;

@ShellComponent
@ShellCommandGroup("Вывод данных")
public class OutputShellController {

    private final TransportService transportService;
    private final CargoService cargoService;

    @Autowired
    public OutputShellController(TransportService transportService, CargoService cargoService) {
        this.transportService = transportService;
        this.cargoService = cargoService;
    }

    @ShellMethod(key = "Информация о транспорте")
    public String getTransportInfo() {
        return transportService.getTransportsInfo();
    }

    @ShellMethod(key = "Информация о грузах")
    public String getCargoInfo() {
        return cargoService.getCargosInfo();
    }

    @ShellMethod(key = "Информация о транспорте с идентификатором")
    public String getCurrentTransportInfo(String id) {
        return transportService.getTransportInfoById(id);
    }

    @ShellMethod(key = "Информация о грузе с названием")
    public String getCurrentCargoInfo(String name) {
        return cargoService.getCargoInfoByName(name);
    }

    @ShellMethod(key = "Сохранить данные в файл")
    public void save(String filePath) {
        transportService.saveToJson(filePath);
    }
}
