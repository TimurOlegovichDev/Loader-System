package ru.liga.loadersystem.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.loadersystem.model.dto.TransportDto;
import ru.liga.loadersystem.service.InitializeService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/loader-system/transport")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TransportRestController {

    private final InitializeService initializeService;

    @PostMapping("/file")
    public ResponseEntity<?> initTransportFromFile(@RequestParam("file") MultipartFile file) {
        try {
            initializeService.initializeTransport(file.getInputStream());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/transport-size")
    public ResponseEntity<?> addTransportWithSize(@RequestBody List<TransportDto> transportDtos) {
        try {
            initializeService.initializeTransport(transportDtos);
            return ResponseEntity.ok().body(transportDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
