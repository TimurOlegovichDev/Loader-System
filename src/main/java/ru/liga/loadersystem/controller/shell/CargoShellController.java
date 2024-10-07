package ru.liga.loadersystem.controller.shell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loadersystem.service.CargoRepositoryService;
import ru.liga.loadersystem.service.InitializeService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Slf4j
@ShellComponent
@ShellCommandGroup("Управление грузами")
public class CargoShellController {

    private final InitializeService initializeService;
    private final CargoRepositoryService cargoRepositoryService;

    @Autowired
    public CargoShellController(InitializeService initializeService,
                                CargoRepositoryService cargoRepositoryService) {
        this.initializeService = initializeService;
        this.cargoRepositoryService = cargoRepositoryService;
    }

    @ShellMethod(key = "Инициализировать груз из файла")
    public void initCargosFromFile(String filePath) throws FileNotFoundException {
        initializeService.initializeCargos(new FileInputStream(filePath));
    }

    @ShellMethod(key = "Добавить посылку")
    public String addCargo(String cargoName, String cargoForm) {
        return cargoRepositoryService.create(cargoName, cargoForm);
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