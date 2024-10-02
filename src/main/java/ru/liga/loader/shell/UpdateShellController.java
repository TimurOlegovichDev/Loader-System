package ru.liga.loader.shell;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.service.CargoService;

@ShellComponent
@ShellCommandGroup("Изменение данных")
public class UpdateShellController {

    private final CargoService cargoService;

    @Autowired
    public UpdateShellController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @ShellMethod(key = "Изменить форму посылки")
    public String setCargoForm(String name, String form) {
        return cargoService.setForm(
                name,
                form
        );
    }

    @ShellMethod(key = "Изменить тип посылки")
    public String setCargoType(String name, char type) {
        return cargoService.setType(
                name,
                type
        );
    }

    @ShellMethod(key = "Изменить имя посылки")
    public String setCargoName(String name, String newName) {
        return cargoService.setName(
                name,
                newName
        );
    }
}
