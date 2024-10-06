package ru.liga.loader.initializers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.factory.transport.TruckFactory;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportJsonStructure;
import ru.liga.loader.model.structure.TransportSizeStructure;
import ru.liga.loader.service.JsonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TruckInitializerTest {

    @Mock
    private JsonService jsonService;

    @InjectMocks
    private TruckInitializer truckInitializer;

    @Test
    void testInitialize_EmptyList_ReturnsEmptyList() {
        List<TransportSizeStructure> sizeStructures = new ArrayList<>();
        List<Transport> transports = truckInitializer.initialize(sizeStructures);
        assertNotNull(transports);
        assertEquals(0, transports.size());
    }

    @Test
    void testInitialize_ValidSizeStructures_ReturnsTransports() {
        TransportSizeStructure sizeStructure = new TransportSizeStructure(1, 1);
        List<TransportSizeStructure> sizeStructures = List.of(sizeStructure);
        Transport transport = new TruckFactory().createTransport(
                sizeStructure.width(),
                sizeStructure.height()
        );
        List<Transport> transports = truckInitializer.initialize(sizeStructures);
        assertNotNull(transports);
        assertEquals(1, transports.size());
        assertArrayEquals(transport.getCharBody(), transports.get(0).getCharBody());
    }

    @Test
    void testInitializeFromJson_EmptyFile_ReturnsEmptyMap() {
        when(jsonService.read(TransportJsonStructure.class, "filepath")).thenReturn(List.of());
        Map<Transport, List<Cargo>> map = truckInitializer.initializeFromJson("filepath");
        assertNotNull(map);
        assertEquals(0, map.size());
    }

    @Test
    void testInitializeFromJson_ValidJson_ReturnsTransportMap() {
        TransportJsonStructure transportJsonStructure = new TransportJsonStructure(
                UUID.randomUUID(),
                "A",
                List.of(new Cargo("cargo1", "A"))
        );
        when(jsonService.read(TransportJsonStructure.class, "filepath")).thenReturn(List.of(transportJsonStructure));
        Map<Transport, List<Cargo>> map = truckInitializer.initializeFromJson("filepath");
        assertNotNull(map);
        assertEquals(1, map.size());
        assertEquals(new TruckFactory().createTransport(transportJsonStructure), map.keySet().iterator().next());
        assertEquals(transportJsonStructure.cargos(), map.values().iterator().next());
    }
}