package ru.liga.loadersystem.controller.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loadersystem.service.CargoRepositoryService;
import ru.liga.loadersystem.service.TransportRepositoryService;

import java.util.UUID;

@ShellComponent
@ShellCommandGroup("Вывод данных")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OutputShellController {

    private final TransportRepositoryService transportRepositoryService;
    private final CargoRepositoryService cargoRepositoryService;

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
        return transportRepositoryService.getTransportInfoById(UUID.fromString(id));
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
