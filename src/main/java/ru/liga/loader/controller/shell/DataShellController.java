package ru.liga.loader.controller.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.service.CargoRepositoryService;
import ru.liga.loader.service.TransportRepositoryService;

import java.util.UUID;

@ShellComponent
@ShellCommandGroup("Управление хранилищем")
public class DataShellController {

    private final TransportRepositoryService transportRepositoryService;
    private final CargoRepositoryService cargoRepositoryService;

    @Autowired
    public DataShellController(TransportRepositoryService transportRepositoryService,
                               CargoRepositoryService cargoRepositoryService) {
        this.transportRepositoryService = transportRepositoryService;
        this.cargoRepositoryService = cargoRepositoryService;
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
