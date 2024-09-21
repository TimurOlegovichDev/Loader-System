package algorithms;

import loader.algorithms.LoadingCargoAlgorithm;
import loader.controllers.InitController;
import loader.controllers.Repository;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.exceptions.NoPlaceException;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import loader.model.enums.AlgorithmTypes;
import loader.utils.CargoCounter;
import loader.utils.FileHandler;
import loader.utils.initializers.CargoInitializer;
import loader.utils.initializers.TruckInitializer;
import loader.utils.json.JsonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AlgoTests {

    private InitController initController;
    private Repository repository;

    @Before
    public void setUp() {
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
                () -> AlgorithmTypes.MES.getAlgorithm().execute(
                        repository.getCargoData(),
                        null
                )
        );
        Assert.assertThrows(
                NoPlaceException.class,
                () -> AlgorithmTypes.MES.getAlgorithm().execute(
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
        LoadingCargoAlgorithm algorithm = AlgorithmTypes.EL.getAlgorithm();
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
        LoadingCargoAlgorithm algorithm = AlgorithmTypes.EL.getAlgorithm();

        // Then
        Assert.assertThrows(
                NoPlaceException.class,
                () -> algorithm.execute(cargoData, transportData)
        );
        System.out.println(transportData);
    }

}
