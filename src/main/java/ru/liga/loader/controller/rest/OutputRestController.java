package ru.liga.loader.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.loader.service.CargoRepositoryService;
import ru.liga.loader.service.TransportRepositoryService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@RestController
@RequestMapping("/loader-system")
public class OutputRestController {

    private final TransportRepositoryService transportRepositoryService;
    private final CargoRepositoryService cargoRepositoryService;

    @Autowired
    public OutputRestController(TransportRepositoryService transportRepositoryService,
                                CargoRepositoryService cargoRepositoryService) {
        this.transportRepositoryService = transportRepositoryService;
        this.cargoRepositoryService = cargoRepositoryService;
    }

    @GetMapping("/transports")
    public ResponseEntity<String> getAllTransports() {
        return ResponseEntity.ok(transportRepositoryService.getTransportsInfo());
    }

    @GetMapping("/transports/{id}")
    public ResponseEntity<String> getTransportById(@PathVariable UUID id) {
        return ResponseEntity.ok(transportRepositoryService.getTransportInfoById(id));
    }

    @GetMapping("/cargos")
    public ResponseEntity<String> getAllCargos() {
        return ResponseEntity.ok(cargoRepositoryService.getCargosInfo());
    }

    @GetMapping("/cargos/{name}")
    public ResponseEntity<String> getCargoById(@PathVariable String name) {
        return ResponseEntity.ok(cargoRepositoryService.getCargoInfoByName(name));
    }

    @GetMapping("/download")
    public ResponseEntity<Void> downloadFile(HttpServletResponse response) throws IOException {
        transportRepositoryService.saveToJson("D:\\WorkSpaces\\Java\\LoaderSystem\\some.json");
        File file = new File("D:\\WorkSpaces\\Java\\LoaderSystem\\some.json");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=myfile.json");
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
        Files.copy(file.toPath(), response.getOutputStream());
        file.delete();
        return ResponseEntity.ok().build();
    }
}