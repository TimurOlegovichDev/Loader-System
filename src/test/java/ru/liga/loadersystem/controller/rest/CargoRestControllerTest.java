package ru.liga.loadersystem.controller.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.loadersystem.service.CargoRepositoryService;
import ru.liga.loadersystem.service.InitializeService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CargoRestControllerTest {

    @InjectMocks
    private CargoRestController cargoRestController;

    @Mock
    private InitializeService initializeService;

    @Mock
    private CargoRepositoryService cargoRepositoryService;

    @Test
    public void testInitCargosFromFileSuccess() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(" dummy data ".getBytes()));
        ResponseEntity<String> response = cargoRestController.initCargosFromFile(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Груз успешно инициализирован!", response.getBody());
        verify(initializeService).initializeCargos(file.getInputStream());
    }

    @Test
    public void testInitCargosFromFileFailure() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenThrow(new IOException("Error reading file"));
        ResponseEntity<String> response = cargoRestController.initCargosFromFile(file);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error reading file", response.getBody());
    }

    @Test
    public void testAddCargoSuccess() {
        String name = "Test Cargo";
        String form = "Test Form";
        when(cargoRepositoryService.create(name, form)).thenReturn("Cargo created successfully");
        ResponseEntity<String> response = cargoRestController.addCargo(name, form);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cargo created successfully", response.getBody());
        verify(cargoRepositoryService).create(name, form);
    }

    @Test
    public void testAddCargoFailure() {
        String name = "Test Cargo";
        String form = "Test Form";
        when(cargoRepositoryService.create(name, form)).thenThrow(new RuntimeException("Error creating cargo"));
        ResponseEntity<String> response = cargoRestController.addCargo(name, form);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error creating cargo", response.getBody());
    }

    @Test
    public void testSetCargoFormSuccess() {
        String name = "Test Cargo";
        String form = "New Form";
        when(cargoRepositoryService.setForm(name, form)).thenReturn("Form updated successfully");
        ResponseEntity<String> response = cargoRestController.setCargoForm(name, form);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Form updated successfully", response.getBody());
        verify(cargoRepositoryService).setForm(name, form);
    }

    @Test
    public void testSetCargoFormFailure() {
        String name = "Test Cargo";
        String form = "New Form";
        when(cargoRepositoryService.setForm(name, form)).thenThrow(new RuntimeException("Error updating form"));
        ResponseEntity<String> response = cargoRestController.setCargoForm(name, form);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error updating form", response.getBody());
    }

    @Test
    public void testSetCargoTypeSuccess() {
        String name = "Test Cargo";
        char type = 'T';
        when(cargoRepositoryService.setType(name, type)).thenReturn("Type updated successfully");
        ResponseEntity<String> response = cargoRestController.setCargoType(name, type);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Type updated successfully", response.getBody());
        verify(cargoRepositoryService).setType(name, type);
    }

    @Test
    public void testSetCargoTypeFailure() {
        String name = "Test Cargo";
        char type = 'T';
        when(cargoRepositoryService.setType(name, type)).thenThrow(new RuntimeException("Error updating type"));
        ResponseEntity<String> response = cargoRestController.setCargoType(name, type);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error updating type", response.getBody());
    }

    @Test
    public void testSetCargoNameSuccess() {
        String name = "Test Cargo";
        String newName = "New Cargo Name";
        when(cargoRepositoryService.setName(name, newName)).thenReturn("Name updated successfully");
        ResponseEntity<String> response = cargoRestController.setCargoName(name, newName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Name updated successfully", response.getBody());
        verify(cargoRepositoryService).setName(name, newName);
    }

    @Test
    public void testSetCargoNameFailure() {
        String name = "Test Cargo";
        String newName = "New Cargo Name";
        when(cargoRepositoryService.setName(name, newName)).thenThrow(new RuntimeException("Error updating name"));
        ResponseEntity<String> response = cargoRestController.setCargoName(name, newName);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error updating name", response.getBody());
    }
}