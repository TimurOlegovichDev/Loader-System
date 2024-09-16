import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.algorithms.Algorithm;
import loader_system.model.algorithms.MinimumEmptySpace;
import loader_system.model.dto.transport.TruckDto;
import loader_system.model.entites.cargos.Cargo;
import loader_system.model.entites.transports.Transport;
import loader_system.model.factories.cargo.BoxFactory;
import loader_system.model.factories.transport.TruckFactory;
import loader_system.model.utils.json.JsonWriter;
import loader_system.model.utils.json.JsonReader;
import org.junit.Assert;
import org.junit.Test;

public class JsonTests {

    private final JsonWriter converter = new JsonWriter();
    private final JsonReader reader = new JsonReader();
    private final String TEST_FILE_PATH = "D:\\JavaWorkSpace\\LoaderSystem\\src\\test\\jsons\\truck.json";

    @Test
    public void test_write_and_read_truck_json() throws Exception {
        Transport transport = new TruckFactory().createTransport();
        Cargo box = new BoxFactory().createCargo(new char[][]{
                {'4', '4'},
                {'4', '4'}
        });
        Cargo box2 = new BoxFactory().createCargo(new char[][]{
                {'7', '7', '7', '7'},
                {'7', '7', '7'}
        });
        CargoData cargoData = new CargoData();
        cargoData.add(box);
        cargoData.add(box2);
        TransportData transportData = new TransportData();
        transportData.add(transport);
        Algorithm algorithm = new MinimumEmptySpace();
        algorithm.execute(cargoData, transportData);
        TruckDto dto = new TruckDto(transport.getBody(), transportData.getBoxes(transport));
        converter.writeObject(dto, TEST_FILE_PATH);
        TruckDto truckDto = reader.readObject(TruckDto.class, TEST_FILE_PATH);
        Assert.assertArrayEquals(truckDto.getBody(), dto.getBody());
    }

}
