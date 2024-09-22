package loadertests.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import loader.algorithms.LoadingCargoAlgorithm;
import loader.db.CargoDataManager;
import loader.db.TransportDataManager;
import loader.factories.cargo.DefaultCargoFactory;
import loader.factories.transport.TruckFactory;
import loader.model.dto.TransportDto;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import loader.model.enums.AlgorithmTypes;
import loader.utils.json.JsonReader;
import loader.utils.json.JsonService;
import loader.utils.json.JsonWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonServiceTest {

    private final CargoDataManager cargoDataManager = new CargoDataManager();
    private final TransportDataManager transportDataManager = new TransportDataManager();
    private final LoadingCargoAlgorithm algorithm = AlgorithmTypes.EL.getAlgorithm();
    private final JsonService jsonService = new JsonService(
            new JsonWriter(new ObjectMapper()),
            new JsonReader(new ObjectMapper())
    );

    private final String TEST_FILE_PATH = "D:\\WorkSpaces\\Java\\LoaderSystem\\src\\test\\resources\\jsons\\valid.json";
    private final int TRUCKS_NUMBER_IN_FILE = 1;

    @BeforeEach
    public void setUp() {
        Transport transport = new TruckFactory().createTransport();
        Cargo box = new DefaultCargoFactory().createCargo(new char[][]{
                {'4', '4'},
                {'4', '4'}
        });
        Cargo box2 = new DefaultCargoFactory().createCargo(new char[][]{
                {'7', '7', '7', '7'},
                {'7', '7', '7'}
        });
        cargoDataManager.add(box);
        cargoDataManager.add(box2);
        transportDataManager.add(transport);
        algorithm.execute(cargoDataManager, transportDataManager);
    }

    @Test
    public void test_write_and_read_truck_to_data() {
        List<TransportDto> transportDtos = new ArrayList<>();
        for (Transport transport : transportDataManager.getData()) {
            transportDtos.add(new TransportDto(transport.getBody(), transportDataManager.getCargos(transport)));
            transportDataManager.remove(transport);
        }
        assertTrue(transportDataManager.getData().isEmpty());
        jsonService.writeObject(transportDtos, TEST_FILE_PATH);
        transportDtos.clear();
        transportDtos = jsonService.read(TransportDto.class, TEST_FILE_PATH);
        for (TransportDto transportDto : transportDtos) {
            Transport transport = new TruckFactory().createTransport(transportDto.getBody());
            transportDataManager.add(transport);
            for (Cargo cargo : transportDto.getCargos()) {
                transportDataManager.addCargoInTransport(transport, cargo);
            }
        }
        assertEquals(transportDataManager.getData().size(), TRUCKS_NUMBER_IN_FILE);
    }

    @Test
    public void test_read_truck_json() {
        List<TransportDto> transportDtos = jsonService.read(TransportDto.class, TEST_FILE_PATH);
        assertEquals(transportDtos.size(), TRUCKS_NUMBER_IN_FILE);
        Transport transportMain = new TruckFactory().createTransport();
        TransportDataManager transportDataManager2 = new TransportDataManager();
        transportDataManager2.add(transportMain);
        algorithm.execute(cargoDataManager, transportDataManager2);
        for (TransportDto transportDto : transportDtos) {
            Transport transport = new TruckFactory().createTransport(transportDto.getBody());
            transportDataManager.add(transport);
            for (Cargo cargo : transportDto.getCargos()) {
                transportDataManager.addCargoInTransport(transport, cargo);
            }
            System.out.println(transportDataManager.getCargos(transport));
        }
        assertArrayEquals(transportDataManager.getData().get(0).getBody(), transportMain.getBody());
    }

    @Test
    void testWriteObject() {
        Cargo cargo = new Cargo(new char[][]{{'1'}});
        jsonService.writeObject(cargo, "cargo.json");
        File file = new File("cargo.json");
        assertTrue(file.exists());
    }

    @Test
    void testRead() {
        Cargo cargo = new Cargo(new char[][]{{'1'}});
        List<Cargo> cargosToWrite = new ArrayList<>();
        cargosToWrite.add(cargo);
        jsonService.writeObject(cargosToWrite, "cargo.json");
        List<Cargo> cargosAfterRead = jsonService.read(Cargo.class, "cargo.json");
        assertEquals(1, cargosAfterRead.size());
        assertArrayEquals(cargo.getForm(),
                cargosAfterRead.get(0).getForm()
        );
    }

    @Test
    void testReadIOException() {
        List<Cargo> cargos = jsonService.read(Cargo.class, "non-existent-file.json");
        assertEquals(0, cargos.size());
    }

}
