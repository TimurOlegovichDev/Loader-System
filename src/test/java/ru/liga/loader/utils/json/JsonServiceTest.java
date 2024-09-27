package ru.liga.loader.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.loader.algorithm.LoadingCargoAlgorithm;
import ru.liga.loader.db.CargoDataManager;
import ru.liga.loader.db.TransportDataManager;
import ru.liga.loader.enums.AlgorithmTypes;
import ru.liga.loader.factory.cargo.DefaultCargoFactory;
import ru.liga.loader.factory.transport.TruckFactory;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.CargoJsonStructure;
import ru.liga.loader.model.structure.TransportJsonStructure;
import ru.liga.loader.util.json.JsonReader;
import ru.liga.loader.util.json.JsonService;
import ru.liga.loader.util.json.JsonWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonServiceTest {

    private final CargoDataManager cargoDataManager = new CargoDataManager(new HashMap<>());
    private final TransportDataManager transportDataManager = new TransportDataManager(new HashMap<>());
    private final LoadingCargoAlgorithm algorithm = AlgorithmTypes.createElAlgorithm(
            transportDataManager,
            cargoDataManager
    );
    private final JsonService jsonService = new JsonService(
            new JsonWriter(new ObjectMapper()),
            new JsonReader(new ObjectMapper())
    );

    private final String TEST_FILE_PATH = "D:\\WorkSpaces\\Java\\LoaderSystem\\src\\test\\resources\\jsons\\valid.json";
    private final int TRUCKS_NUMBER_IN_FILE = 1;

    @BeforeEach
    public void setUp() {
        Transport transport = new TruckFactory().createTransport();
        Cargo box = new DefaultCargoFactory().createCargo(
                "",
                new char[][]{
                        {'4', '4'},
                        {'4', '4'}
                });
        Cargo box2 = new DefaultCargoFactory().createCargo(
                "",
                new char[][]{
                        {'7', '7', '7', '7'},
                        {'7', '7', '7'}
                });
        cargoDataManager.add(box);
        cargoDataManager.add(box2);
        transportDataManager.add(transport);
        algorithm.execute();
    }

    @Test
    public void test_write_and_read_truck_to_data() {
        List<TransportJsonStructure> transportJsonStructures = new ArrayList<>();
        for (Transport transport : transportDataManager.getData()) {
            transportJsonStructures.add(new TransportJsonStructure(transport.getBody(), transportDataManager.getCargos(transport)));
            transportDataManager.remove(transport);
        }
        assertTrue(transportDataManager.getData().isEmpty());
        jsonService.writeObject(transportJsonStructures, TEST_FILE_PATH);
        transportJsonStructures.clear();
        transportJsonStructures = jsonService.read(TransportJsonStructure.class, TEST_FILE_PATH);
        for (TransportJsonStructure transportJsonStructure : transportJsonStructures) {
            Transport transport = new TruckFactory().createTransport(transportJsonStructure.getBody());
            transportDataManager.add(transport);
            for (Cargo cargo : transportJsonStructure.getCargos()) {
                transportDataManager.addCargoInTransport(transport, cargo);
            }
        }
        assertEquals(transportDataManager.getData().size(), TRUCKS_NUMBER_IN_FILE);
    }

    @Test
    public void test_read_truck_json() {
        List<TransportJsonStructure> transportJsonStructures = jsonService.read(TransportJsonStructure.class, TEST_FILE_PATH);
        assertEquals(transportJsonStructures.size(), TRUCKS_NUMBER_IN_FILE);
        Transport transportMain = new TruckFactory().createTransport();
        TransportDataManager transportDataManager2 = new TransportDataManager(new HashMap<>());
        transportDataManager2.add(transportMain);
        AlgorithmTypes.createElAlgorithm(
                transportDataManager2,
                cargoDataManager
        ).execute();
        for (TransportJsonStructure transportJsonStructure : transportJsonStructures) {
            Transport transport = new TruckFactory().createTransport(transportJsonStructure.getBody());
            transportDataManager.add(transport);
            for (Cargo cargo : transportJsonStructure.getCargos()) {
                transportDataManager.addCargoInTransport(transport, cargo);
            }
            System.out.println(transportDataManager.getCargos(transport));
        }
        assertArrayEquals(transportDataManager.getData().get(0).getBody(), transportMain.getBody());
    }

    @Test
    void test_write_and_read_cargo_json() {
        Cargo cargo = new Cargo("Small box", new char[][]{{'1'}});
        jsonService.writeObject(new ArrayList<>(List.of(cargo)), "D:\\WorkSpaces\\Java\\LoaderSystem\\src\\test\\resources\\jsons\\valid-cargos.json");
        List<CargoJsonStructure> structures = jsonService.read(CargoJsonStructure.class, "D:\\WorkSpaces\\Java\\LoaderSystem\\src\\test\\resources\\jsons\\valid-cargos.json");
        assertArrayEquals(
                cargo.getForm(),
                structures.get(0).getForm()
        );
    }


    @Test
    void testReadIOException() {
        List<Cargo> cargos = jsonService.read(Cargo.class, "non-existent-file.json");
        assertEquals(0, cargos.size());
    }

}
