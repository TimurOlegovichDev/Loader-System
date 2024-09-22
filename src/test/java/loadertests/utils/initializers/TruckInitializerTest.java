package loader.utils.initializers;

import loader.factories.transport.TruckFactory;
import loader.model.dto.TransportDto;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import loader.utils.json.JsonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Mock
    private TruckFactory truckFactory;

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
        List<TransportDto> transportDtos = Arrays.asList(
                new TransportDto(new char[][]{{'+', '1', '+'}}, List.of(new Cargo(new char[][]{{'1'}}))),
                new TransportDto(new char[][]{{'+', '1', '+'}}, List.of(new Cargo(new char[][]{{'1'}})))
        );

        when(jsonService.read(TransportDto.class, VALID_JSON)).thenReturn(transportDtos);

        Map<Transport, List<Cargo>> actualMap = truckInitializer.initializeFromJson(VALID_JSON);
        assertEquals(2, actualMap.size());
        assertNotNull(actualMap);
        verify(jsonService).read(TransportDto.class, VALID_JSON);
    }

    @Test
    void testInitializeFromJson_withInvalidJsonFile_throwsException() {
        when(jsonService.read(TransportDto.class, INVALID_JSON)).thenThrow(new RuntimeException("Invalid JSON file"));

        assertThrows(
                RuntimeException.class,
                () -> truckInitializer.initializeFromJson(INVALID_JSON)
        );
    }
}