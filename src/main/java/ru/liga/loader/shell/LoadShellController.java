package ru.liga.loader.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.DefaultCrudCargoRepository;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.service.LoadingService;

import java.util.List;


@ShellComponent
@ShellCommandGroup("Погрузка грузов")
public class LoadShellController {

    private final LoadingService loadingService;
    private final TransportCrudRepository transportRepository;
    private final DefaultCrudCargoRepository defaultCrudCargoRepository;

    @Autowired
    public LoadShellController(LoadingService loadingService,
                               @Qualifier("transportCrudRepository") TransportCrudRepository transportRepository,
                               DefaultCrudCargoRepository defaultCrudCargoRepository) {
        this.loadingService = loadingService;
        this.transportRepository = transportRepository;
        this.defaultCrudCargoRepository = defaultCrudCargoRepository;
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
                defaultCrudCargoRepository.findAllUnique()
        );
    }
}
