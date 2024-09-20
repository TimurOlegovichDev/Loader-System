package algorithms;

import loader.algorithms.EvenLoadingAlgorithm;
import loader.algorithms.MinimumEmptySpaceAlgorithm;
import loader.controllers.InitController;
import loader.controllers.Repository;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.exceptions.NoPlaceException;
import loader.model.entites.cargos.Cargo;
import loader.model.entites.transports.Transport;
import loader.utils.CargoCounter;
import loader.utils.FileHandler;
import loader.utils.initializers.CargoInitializer;
import loader.utils.initializers.TruckInitializer;
import loader.utils.json.JsonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlgoTests {

    private InitController initController;
    private Repository repository;

    @Before
    public void setUp() throws IOException {
        repository = new Repository(new TransportData(), new CargoData());
        this.initController = new InitController(
                new TruckInitializer(),
                new CargoInitializer(),
                repository,
                new CargoCounter()
        );
        initController.initializeCargos(new FileHandler(
                new JsonService()
        ).read("D:\\WorkSpaces\\Java\\LoaderSystem\\src\\test\\resources\\txts\\InvalidData"));
    }

    @Test
    public void noPlaceExcThrowTestIfNullTransportData() {
        Assert.assertThrows(
                NoPlaceException.class,
                () -> new EvenLoadingAlgorithm().execute(
                        repository.getCargoData(),
                        null
                )
        );
        Assert.assertThrows(
                NoPlaceException.class,
                () -> new MinimumEmptySpaceAlgorithm().execute(
                        repository.getCargoData(),
                        null
                )
        );
    }

    @Test
    public void testLoadCargoSuccessfully() {
        CargoData cargoData = new CargoData();
        cargoData.add(new Cargo(new char[][]{{'1'}})); // 1x1 cargo
        TransportData transportData = new TransportData();
        transportData.add(new Transport());
        EvenLoadingAlgorithm algorithm = new EvenLoadingAlgorithm();
        algorithm.execute(cargoData, transportData);
        Transport transport = transportData.getData().get(0);
        List<Cargo> testList = new ArrayList<>();
        testList.add(new Cargo(new char[][]{{'1'}}));
        Assert.assertArrayEquals(testList.get(0).getForm(), transportData.getCargos(transport).get(0).getForm());
        Assert.assertEquals(1, transportData.getCargoWeightInTransport(transport));
    }

    @Test
    public void testNoPlaceFoundForCargo() {
        // Given
        CargoData cargoData = new CargoData();
        cargoData.add(new Cargo(new char[][]{{'2'}, {'2'}})); // 2x1 cargo
        TransportData transportData = new TransportData();
        transportData.add(new Transport(new char[][]{{' ', '1'}})); // truck with no space

        // When
        EvenLoadingAlgorithm algorithm = new EvenLoadingAlgorithm();

        // Then
        Assert.assertThrows(
                NoPlaceException.class,
                () -> algorithm.execute(cargoData, transportData)
        );
        System.out.println(transportData);
    }

}
