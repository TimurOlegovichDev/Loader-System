package ru.liga.loader.controller.shell;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.service.CargoRepositoryService;

@ShellComponent
@ShellCommandGroup("Изменение данных")
public class UpdateShellController {

    private final CargoRepositoryService cargoRepositoryService;

    @Autowired
    public UpdateShellController(CargoRepositoryService cargoRepositoryService) {
        this.cargoRepositoryService = cargoRepositoryService;
    }

    @ShellMethod(key = "Изменить форму посылки")
    public String setCargoForm(String name, String form) {
        return cargoRepositoryService.setForm(
                name,
                form
        );
    }

    @ShellMethod(key = "Изменить тип посылки")
    public String setCargoType(String name, char type) {
        return cargoRepositoryService.setType(
                name,
                type
        );
    }

    @ShellMethod(key = "Изменить имя посылки")
    public String setCargoName(String name, String newName) {
        return cargoRepositoryService.setName(
                name,
                newName
        );
    }
}
