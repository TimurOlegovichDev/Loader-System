package ru.liga.loadersystem.controller.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.loadersystem.model.dto.CargoDto;
import ru.liga.loadersystem.service.CargoRepositoryService;
import ru.liga.loadersystem.service.InitializeService;

@RestController
@RequestMapping("/loader-system/cargo")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CargoRestController {

    private final InitializeService initializeService;
    private final CargoRepositoryService cargoRepositoryService;

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
    public ResponseEntity<?> addCargo(@RequestBody CargoDto cargo) {
        try {
            return ResponseEntity.ok(
                    cargoRepositoryService.create(
                            cargo.name(),
                            cargo.form()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{name}/form")
    public ResponseEntity<?> setCargoForm(@RequestBody CargoDto cargo) {
        try {
            return ResponseEntity.ok(
                    cargoRepositoryService.setForm(cargo.name(),
                            cargo.form()
                    ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{name}/type")
    public ResponseEntity<?> setCargoType(@PathVariable("name") String name,
                                          @RequestParam("type") char type) {
        try {
            return ResponseEntity.ok()
                    .body(
                            cargoRepositoryService.setType(name, type)
                    );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{name}/name")
    public ResponseEntity<?> setCargoName(@PathVariable("name") String name,
                                          @RequestParam("newName") String newName) {
        try {
            return ResponseEntity.ok()
                    .body(
                            cargoRepositoryService.setName(name, newName)
                    );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
