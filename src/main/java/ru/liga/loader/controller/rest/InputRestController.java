package ru.liga.loader.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.loader.model.structure.TransportSizeStructure;
import ru.liga.loader.parser.StringParser;
import ru.liga.loader.service.CargoRepositoryService;
import ru.liga.loader.service.InitializeService;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/loader-system/init")
public class InputRestController {

    private final InitializeService initializeService;
    private final StringParser<List<TransportSizeStructure>> stringParser;
    private final CargoRepositoryService cargoRepositoryService;

    @Autowired
    public InputRestController(InitializeService initializeService, StringParser<List<TransportSizeStructure>> stringParser, CargoRepositoryService cargoRepositoryService) {
        this.initializeService = initializeService;
        this.stringParser = stringParser;
        this.cargoRepositoryService = cargoRepositoryService;
    }

    @PostMapping("/file/cargo")
    public ResponseEntity<String> initCargosFromFile(@RequestParam("file") MultipartFile file) {
        try {
            initializeService.initializeCargos(file.getInputStream());
            return ResponseEntity.ok("Груз успешно инициализирован!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/file/transport")
    public ResponseEntity<String> initTransportFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            initializeService.initializeTransport(file.getInputStream());
            return ResponseEntity.ok("Груз успешно инициализирован!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/file/transport-size")
    public ResponseEntity<String> addTransportWithSize(@RequestBody String input) {
        try {
            initializeService.initializeTransport(stringParser.parse(input));
            return ResponseEntity.ok("Груз успешно инициализирован!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cargo")
    public ResponseEntity<String> addCargo(@RequestParam("name") String name,
                                           @RequestParam("form") String form) {
        return ResponseEntity.ok(
                cargoRepositoryService.create(
                        name,
                        form
                )
        );
    }
}
