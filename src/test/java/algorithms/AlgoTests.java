package algorithms;

import loader.algorithms.LoadingCargoAlgorithm;
import loader.controllers.TransportationCargoContainer;
import loader.db.CargoData;
import loader.db.TransportData;
import loader.exceptions.NoPlaceException;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import loader.model.enums.AlgorithmTypes;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlgoTests {

    private final TransportationCargoContainer transportationCargoContainer =
            new TransportationCargoContainer(
                    new TransportData(),
                    new CargoData()
            );

    @Test
    public void noPlaceExcThrowTestIfNullTransportData() {
        assertThrows(
                NoPlaceException.class,
                () -> AlgorithmTypes.MES.getAlgorithm().execute(
                        transportationCargoContainer.getCargoData(),
                        null
                )
        );
        assertThrows(
                NoPlaceException.class,
                () -> AlgorithmTypes.MES.getAlgorithm().execute(
                        transportationCargoContainer.getCargoData(),
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
        assertArrayEquals(testList.get(0).getForm(), transportData.getCargos(transport).get(0).getForm());
        assertEquals(1, transportData.getCargoWeightInTransport(transport));
    }

    @Test
    public void testNoPlaceFoundForCargo() {
        CargoData cargoData = new CargoData();
        cargoData.add(new Cargo(new char[][]{{'2'}, {'2'}})); // 2x1 cargo
        TransportData transportData = new TransportData();
        transportData.add(new Transport(new char[][]{{' ', '1'}})); // truck with no space
        LoadingCargoAlgorithm algorithm = AlgorithmTypes.EL.getAlgorithm();
        assertThrows(
                NoPlaceException.class,
                () -> algorithm.execute(cargoData, transportData)
        );
    }

}
