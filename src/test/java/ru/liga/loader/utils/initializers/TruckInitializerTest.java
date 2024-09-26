package ru.liga.loader.utils.initializers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportJsonStructure;
import ru.liga.loader.util.initializers.TruckInitializer;
import ru.liga.loader.util.json.JsonService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TruckInitializerTest {

    private final String VALID_JSON = "D:\\WorkSpaces\\Java\\LoaderSystem\\src\\test\\resources\\jsons\\valid.json";
    private final String INVALID_JSON = "D:\\WorkSpaces\\Java\\LoaderSystem\\src\\test\\resources\\jsons\\invalid.json";

    @Mock
    private JsonService jsonService;

    @InjectMocks
    private TruckInitializer truckInitializer;

    @Test
    void testInitialize_withValidNumberOfTransport_returnsListOfTransports() {
        int numberOfTransport = 3;
        List<Transport> expectedTransports = new ArrayList<>();
        for (int i = 0; i < numberOfTransport; i++) {
            expectedTransports.add(new Transport());
        }

        List<Transport> actualTransports = truckInitializer.initialize(numberOfTransport);
        assertEquals(expectedTransports.size(), actualTransports.size());
        assertNotNull(actualTransports);
    }

    @Test
    void testInitializeFromJson_withValidJsonFile_returnsMapOfTransportsAndCargos() {
        List<TransportJsonStructure> transportJsonStructures = Arrays.asList(
                new TransportJsonStructure(new char[][]{{'+', '1', '+'}}, List.of(new Cargo(new char[][]{{'1'}}))),
                new TransportJsonStructure(new char[][]{{'+', '1', '+'}}, List.of(new Cargo(new char[][]{{'1'}})))
        );

        when(jsonService.read(TransportJsonStructure.class, VALID_JSON)).thenReturn(transportJsonStructures);

        Map<Transport, List<Cargo>> actualMap = truckInitializer.initializeFromJson(VALID_JSON);
        assertEquals(2, actualMap.size());
        assertNotNull(actualMap);
        verify(jsonService).read(TransportJsonStructure.class, VALID_JSON);
    }

    @Test
    void testInitializeFromJson_withInvalidJsonFile_throwsException() {
        when(jsonService.read(TransportJsonStructure.class, INVALID_JSON)).thenThrow(new RuntimeException("Invalid JSON file"));

        assertThrows(
                RuntimeException.class,
                () -> truckInitializer.initializeFromJson(INVALID_JSON)
        );
    }
}