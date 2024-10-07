package ru.liga.loadersystem.controller.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.parser.impl.CargoNameParser;
import ru.liga.loadersystem.repository.TransportCrudRepository;
import ru.liga.loadersystem.service.LoadingService;

import java.util.List;


@ShellComponent
@ShellCommandGroup("Управление погрузкой")
public class LoadShellController {

    private final LoadingService loadingService;
    private final TransportCrudRepository transportRepository;
    private final CargoNameParser cargoNameParser;

    @Autowired
    public LoadShellController(LoadingService loadingService,
                               @Qualifier("transportCrudRepository") TransportCrudRepository transportRepository,
                               CargoNameParser cargoNameParser) {
        this.loadingService = loadingService;
        this.transportRepository = transportRepository;
        this.cargoNameParser = cargoNameParser;
    }

    @ShellMethod(key = "Выполнить автоматическую погрузку")
    public void loadCargos(String algoName) {
        loadingService.load(algoName);
    }

    @ShellMethod(key = "Загрузить посылки по названию")
    public void loadCargosByName(String algoName, String cargos) {
        loadingService.selectiveLoad(
                algoName,
                (List<Transport>) transportRepository.findAll(),
                cargoNameParser.parse(cargos)
        );
    }
}
