package ru.liga.loadersystem.controller.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.service.CargoRepositoryService;
import ru.liga.loadersystem.service.InitializeService;

import java.io.FileInputStream;

@Slf4j
@ShellComponent
@ShellCommandGroup("Управление грузами")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CargoShellController {

    private final InitializeService initializeService;
    private final CargoRepositoryService cargoRepositoryService;

    @ShellMethod(key = "Инициализировать груз из файла")
    public String initCargosFromFile(String filePath) {
        try {
            initializeService.initializeCargos(new FileInputStream(filePath));
            return "Инициализация прошла успешно";
        } catch (Exception e) {
            return "При чтении произошла ошибка " + e.getMessage();
        }
    }

    @ShellMethod(key = "Добавить посылку")
    public Cargo addCargo(String cargoName, String cargoForm) {
        return cargoRepositoryService.create(cargoName, cargoForm);
    }

    @ShellMethod(key = "Изменить форму посылки")
    public Cargo setCargoForm(String name, String form) {
        return cargoRepositoryService.setForm(
                name,
                form
        );
    }

    @ShellMethod(key = "Изменить тип посылки")
    public Cargo setCargoType(String name, char type) {
        return cargoRepositoryService.setType(
                name,
                type
        );
    }

    @ShellMethod(key = "Изменить имя посылки")
    public Cargo setCargoName(String name, String newName) {
        return cargoRepositoryService.setName(
                name,
                newName
        );
    }

    @ShellMethod(key = "Удалить груз из системы")
    public void deleteCargoFormSystem(String name, String algoName) {
        cargoRepositoryService.delete(name, algoName);
    }
}