package ru.liga.loader.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.TransportCrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransportRepositoryServiceTest {

    @Mock
    private TransportCrudRepository transportRepository;

    @InjectMocks
    private TransportRepositoryService transportRepositoryService;

    @Test
    void testPercentageOfOccupancy_TransportExists_ReturnsOccupancy() {
        Transport transport = new Transport(1, 1);
        List<Cargo> cargos = new ArrayList<>();
        cargos.add(new Cargo("cargo1", new char[][]{{'A'}}));
        when(transportRepository.getKeys()).thenReturn(List.of(transport));
        when(transportRepository.getCargos(transport)).thenReturn(cargos);
        int result = transportRepositoryService.percentageOfOccupancy(transport);
        assertEquals(100, result);
    }

    @Test
    void testPercentageOfOccupancy_TransportNotExists_ReturnsZero() {
        Transport transport = new Transport(1, 1);
        when(transportRepository.getKeys()).thenReturn(new ArrayList<>());
        int result = transportRepositoryService.percentageOfOccupancy(transport);
        assertEquals(0, result);
    }

    @Test
    void testGetTransportById_TransportExists_ReturnsTransport() {
        Transport transport = new Transport(1, 1);
        when(transportRepository.getKeys()).thenReturn(List.of(transport));
        Optional<Transport> result = transportRepositoryService.getTransportById(transport.getId());
        assertNotNull(result);
        assertEquals(transport, result.orElse(null));
    }

    @Test
    void testGetTransportById_TransportNotExists_ReturnsEmpty() {
        Transport transport = new Transport(1, 1);
        when(transportRepository.getKeys()).thenReturn(new ArrayList<>());
        Optional<Transport> result = transportRepositoryService.getTransportById(transport.getId());
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testUpdateCargosName_CargoExists_UpdatesName() {
        Transport transport = new Transport(1, 1);
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A'}});
        List<Cargo> cargos = new ArrayList<>();
        cargos.add(cargo);
        when(transportRepository.getKeys()).thenReturn(List.of(transport));
        when(transportRepository.getCargos(transport)).thenReturn(cargos);
        transportRepositoryService.updateCargosName("cargo1", "newName");
        assertEquals("newName", cargo.getName());
    }

    @Test
    void testUpdateCargosName_CargoNotExists_DoesNotUpdateName() {
        Transport transport = new Transport(1, 1);
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A'}});
        List<Cargo> cargos = new ArrayList<>();
        cargos.add(cargo);
        when(transportRepository.getKeys()).thenReturn(List.of(transport));
        when(transportRepository.getCargos(transport)).thenReturn(cargos);
        transportRepositoryService.updateCargosName("cargo2", "newName");
        assertEquals("cargo1", cargo.getName());
    }
}