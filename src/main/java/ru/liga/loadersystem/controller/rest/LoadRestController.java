package ru.liga.loadersystem.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.parser.impl.CargoNameParser;
import ru.liga.loadersystem.repository.TransportCrudRepository;
import ru.liga.loadersystem.service.LoadingService;

import java.util.List;

@RestController
@RequestMapping("/loader-system/load")
public class LoadRestController {

    private final LoadingService loadingService;
    private final TransportCrudRepository transportRepository;
    private final CargoNameParser cargoNameParser;

    @Autowired
    public LoadRestController(LoadingService loadingService,
                              @Qualifier("defaultCrudTransportRepository") TransportCrudRepository transportRepository,
                              CargoNameParser cargoNameParser) {
        this.loadingService = loadingService;
        this.transportRepository = transportRepository;
        this.cargoNameParser = cargoNameParser;
    }

    @PostMapping("/automatic")
    public ResponseEntity<String> loadCargos(@RequestParam("algoName") String algoName) {
        try {
            loadingService.load(algoName);
            return ResponseEntity.ok("Погрузка окончена!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cargo-by-name")
    public ResponseEntity<String> loadCargosByName(@RequestParam("algoName") String algoName,
                                                   @RequestParam("names") String cargos) {
        List<Cargo> listToLoad = cargoNameParser.parse(cargos);
        try {
            loadingService.selectiveLoad(
                    algoName,
                    (List<Transport>) transportRepository.findAll(),
                    listToLoad
            );
            return ResponseEntity.ok("Погрузка окончена, загружено посылок: " + listToLoad.size());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}