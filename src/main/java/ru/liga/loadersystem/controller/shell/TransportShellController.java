package ru.liga.loadersystem.controller.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loadersystem.model.dto.TransportDto;
import ru.liga.loadersystem.parser.StringParser;
import ru.liga.loadersystem.service.InitializeService;
import ru.liga.loadersystem.service.TransportRepositoryService;

import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

@ShellComponent
@ShellCommandGroup("Управление транспортом")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TransportShellController {

    private final TransportRepositoryService transportRepositoryService;
    private final StringParser<List<TransportDto>> stringParser;
    private final InitializeService initializeService;


    @ShellMethod(key = "Инициализировать транспорт из файла")
    public String initTransportFromFile(String filePath) {
        try {
            initializeService.initializeTransport(new FileInputStream(filePath));
            return "Груз инициализирован успешно";
        } catch (Exception e) {
            return "При чтении произошла ошибка " + e.getMessage();
        }
    }

    @ShellMethod(key = "Добавить транспорт с указанным размером")
    public void addTransportWithSize(String input) {
        initializeService.initializeTransport(
                stringParser.parse(input)
        );
    }

    @ShellMethod(key = "Удалить транспорт из системы")
    public String deleteTransportFormSystem(String id) {
        return transportRepositoryService.delete(UUID.fromString(id));
    }
}
