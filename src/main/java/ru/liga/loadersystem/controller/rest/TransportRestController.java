package ru.liga.loadersystem.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.loadersystem.model.structure.TransportSizeStructure;
import ru.liga.loadersystem.parser.StringParser;
import ru.liga.loadersystem.service.InitializeService;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/loader-system/transport")
public class TransportRestController {

    private final InitializeService initializeService;
    private final StringParser<List<TransportSizeStructure>> stringParser;

    @Autowired
    public TransportRestController(InitializeService initializeService,
                                   StringParser<List<TransportSizeStructure>> stringParser) {
        this.initializeService = initializeService;
        this.stringParser = stringParser;
    }

    @PostMapping("/file")
    public ResponseEntity<String> initTransportFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            initializeService.initializeTransport(file.getInputStream());
            return ResponseEntity.ok("Груз успешно инициализирован!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/transport-size")
    public ResponseEntity<String> addTransportWithSize(@RequestBody String input) {
        try {
            initializeService.initializeTransport(stringParser.parse(input));
            return ResponseEntity.ok("Транспорт успешно инициализирован!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
