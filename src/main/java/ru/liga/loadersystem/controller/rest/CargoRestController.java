package ru.liga.loadersystem.controller.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.loadersystem.service.CargoRepositoryService;
import ru.liga.loadersystem.service.InitializeService;

@RestController
@RequestMapping("/loader-system/cargo")
public class CargoRestController {

    private final InitializeService initializeService;
    private final CargoRepositoryService cargoRepositoryService;

    @Autowired
    public CargoRestController(InitializeService initializeService,
                               CargoRepositoryService cargoRepositoryService) {
        this.initializeService = initializeService;
        this.cargoRepositoryService = cargoRepositoryService;
    }

    @PostMapping("/file")
    public ResponseEntity<String> initCargosFromFile(@RequestParam("file") MultipartFile file) {
        try {
            initializeService.initializeCargos(file.getInputStream());
            return ResponseEntity.ok("Груз успешно инициализирован!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<String> addCargo(@RequestParam("name") String name,
                                           @RequestParam("form") String form) {
        try {
            return ResponseEntity.ok(
                    cargoRepositoryService.create(
                            name,
                            form
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{name}/form")
    public ResponseEntity<String> setCargoForm(@PathVariable("name") String name,
                                               @RequestParam("form") String form) {
        try {
            return new ResponseEntity<>(
                    cargoRepositoryService.setForm(name, form),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{name}/type")
    public ResponseEntity<String> setCargoType(@PathVariable("name") String name,
                                               @RequestParam("type") char type) {
        try {
            return new ResponseEntity<>(
                    cargoRepositoryService.setType(name, type),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{name}/name")
    public ResponseEntity<String> setCargoName(@PathVariable("name") String name,
                                               @RequestParam("newName") String newName) {
        try {
            return new ResponseEntity<>(
                    cargoRepositoryService.setName(name, newName),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
