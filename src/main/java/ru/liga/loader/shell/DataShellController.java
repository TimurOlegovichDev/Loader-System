package ru.liga.loader.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.service.CargoRepositoryService;
import ru.liga.loader.service.TransportService;

@ShellComponent
@ShellCommandGroup("Управление хранилищем")
public class DataShellController {

    private final TransportService transportService;
    private final CargoRepositoryService cargoRepositoryService;

    @Autowired
    public DataShellController(TransportService transportService,
                               CargoRepositoryService cargoRepositoryService) {
        this.transportService = transportService;
        this.cargoRepositoryService = cargoRepositoryService;
    }

    @ShellMethod(key = "Удалить груз из системы")
    public String deleteCargoFormSystem(String name) {
        return cargoRepositoryService.delete(name);
    }

    @ShellMethod(key = "Удалить транспорт из системы")
    public String deleteTransportFormSystem(String id) {
        return transportService.delete(id);
    }
}
