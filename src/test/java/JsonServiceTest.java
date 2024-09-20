import loader.algorithms.EvenLoadingAlgorithm;
import loader.algorithms.LoadingCargoAlgorithm;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.model.dto.TruckDto;
import loader.model.entites.cargos.Cargo;
import loader.model.entites.transports.Transport;
import loader.factories.cargo.CargoFactory;
import loader.factories.transport.TruckFactory;
import loader.utils.json.JsonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JsonServiceTest {

    private final CargoData cargoData = new CargoData();
    private final TransportData transportData = new TransportData();
    private final LoadingCargoAlgorithm algorithm = new EvenLoadingAlgorithm();
    private final JsonService jsonService = new JsonService();

    private final String TEST_FILE_PATH = "D:\\WorkSpaces\\Java\\LoaderSystem\\src\\test\\resources\\jsons\\truck.json";
    private final int TRUCKS_NUMBER_IN_FILE = 1;

    @Before
    public void setUp() {
        Transport transport = new TruckFactory().createTransport();
        Cargo box = new CargoFactory().createCargo(new char[][]{
                {'4', '4'},
                {'4', '4'}
        });
        Cargo box2 = new CargoFactory().createCargo(new char[][]{
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
        List<TruckDto> truckDtos = new ArrayList<>();
        for(Transport transport : transportData.getData()){
            truckDtos.add(new TruckDto(transport.getBody(), transportData.getCargos(transport)));
            transportData.remove(transport);
        }
        Assert.assertTrue(transportData.getData().isEmpty());
        jsonService.writeObject(truckDtos, TEST_FILE_PATH);
        truckDtos.clear();
        truckDtos = jsonService.read(TruckDto.class, TEST_FILE_PATH);
        for (TruckDto truckDto : truckDtos) {
            Transport transport = new TruckFactory().createTransport(truckDto.getBody());
            transportData.add(transport);
            for (Cargo cargo : truckDto.getCargos()) {
                transportData.addCargoInTransport(transport, cargo);
            }
        }
        Assert.assertEquals(transportData.getData().size(), TRUCKS_NUMBER_IN_FILE);
    }

    @Test
    public void test_read_truck_json() {
        List<TruckDto> truckDtos = new JsonService().read(TruckDto.class, TEST_FILE_PATH);
        Assert.assertEquals(truckDtos.size(), TRUCKS_NUMBER_IN_FILE);
        Transport transportMain = new TruckFactory().createTransport();
        TransportData transportData2 = new TransportData();
        transportData2.add(transportMain);
        algorithm.execute(cargoData, transportData2);
        for (TruckDto truckDto : truckDtos) {
            Transport transport = new TruckFactory().createTransport(truckDto.getBody());
            transportData.add(transport);
            for (Cargo cargo : truckDto.getCargos()) {
                transportData.addCargoInTransport(transport, cargo);
            }
            System.out.println(transportData.getCargos(transport));
        }
        Assert.assertArrayEquals(transportData.getData().get(0).getBody(), transportMain.getBody());
    }

}
