package ru.liga.loadersystem.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.loadersystem.service.CargoRepositoryService;
import ru.liga.loadersystem.service.TransportRepositoryService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/loader-system")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OutputRestController {

    private final TransportRepositoryService transportRepositoryService;
    private final CargoRepositoryService cargoRepositoryService;
    private final ObjectMapper objectMapper;

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
    public ResponseEntity<byte[]> downloadFile() throws IOException {
        byte[] jsonBytes = objectMapper.writeValueAsBytes(
                transportRepositoryService.getStructures()
        );
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .header("Content-Disposition", "attachment; filename=\"info.json\"")
                .body(jsonBytes);
    }
}