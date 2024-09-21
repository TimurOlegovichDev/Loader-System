import loader.algorithms.LoadingCargoAlgorithm;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.factories.cargo.DefaultCargoFactory;
import loader.factories.transport.TruckFactory;
import loader.model.dto.TransportDto;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import loader.model.enums.AlgorithmTypes;
import loader.utils.json.JsonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JsonServiceTest {

    private final CargoData cargoData = new CargoData();
    private final TransportData transportData = new TransportData();
    private final LoadingCargoAlgorithm algorithm = AlgorithmTypes.EL.getAlgorithm();
    private final JsonService jsonService = new JsonService();

    private final String TEST_FILE_PATH = "D:\\WorkSpaces\\Java\\LoaderSystem\\src\\test\\resources\\jsons\\truck.json";
    private final int TRUCKS_NUMBER_IN_FILE = 1;

    @Before
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
        cargoData.add(box);
        cargoData.add(box2);
        transportData.add(transport);
        algorithm.execute(cargoData, transportData);
    }

    @Test
    public void test_write_and_read_truck_to_data() {
        List<TransportDto> transportDtos = new ArrayList<>();
        for (Transport transport : transportData.getData()) {
            transportDtos.add(new TransportDto(transport.getBody(), transportData.getCargos(transport)));
            transportData.remove(transport);
        }
        Assert.assertTrue(transportData.getData().isEmpty());
        jsonService.writeObject(transportDtos, TEST_FILE_PATH);
        transportDtos.clear();
        transportDtos = jsonService.read(TransportDto.class, TEST_FILE_PATH);
        for (TransportDto transportDto : transportDtos) {
            Transport transport = new TruckFactory().createTransport(transportDto.getBody());
            transportData.add(transport);
            for (Cargo cargo : transportDto.getCargos()) {
                transportData.addCargoInTransport(transport, cargo);
            }
        }
        Assert.assertEquals(transportData.getData().size(), TRUCKS_NUMBER_IN_FILE);
    }

    @Test
    public void test_read_truck_json() {
        List<TransportDto> transportDtos = new JsonService().read(TransportDto.class, TEST_FILE_PATH);
        Assert.assertEquals(transportDtos.size(), TRUCKS_NUMBER_IN_FILE);
        Transport transportMain = new TruckFactory().createTransport();
        TransportData transportData2 = new TransportData();
        transportData2.add(transportMain);
        algorithm.execute(cargoData, transportData2);
        for (TransportDto transportDto : transportDtos) {
            Transport transport = new TruckFactory().createTransport(transportDto.getBody());
            transportData.add(transport);
            for (Cargo cargo : transportDto.getCargos()) {
                transportData.addCargoInTransport(transport, cargo);
            }
            System.out.println(transportData.getCargos(transport));
        }
        Assert.assertArrayEquals(transportData.getData().get(0).getBody(), transportMain.getBody());
    }

}
