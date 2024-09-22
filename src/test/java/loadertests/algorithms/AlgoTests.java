package loadertests.algorithms;

import loader.algorithms.LoadingCargoAlgorithm;
import loader.controllers.TransportationDataContainer;
import loader.db.CargoDataManager;
import loader.db.TransportDataManager;
import loader.exceptions.NoPlaceException;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import loader.model.enums.AlgorithmTypes;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlgoTests {

    private final TransportationDataContainer transportationDataContainer =
            new TransportationDataContainer(
                    new TransportDataManager(new HashMap<>()),
                    new CargoDataManager(new ArrayList<>())
            );

    @Test
    public void noPlaceExcThrowTestIfNullTransportData() {
        assertThrows(
                NoPlaceException.class,
                () -> {
                    AlgorithmTypes.MES.getAlgorithm().execute(
                            transportationDataContainer.getCargoDataManager(),
                            null
                    );
                }
        );
        assertThrows(
                NoPlaceException.class,
                () -> {
                    AlgorithmTypes.MES.getAlgorithm().execute(
                            transportationDataContainer.getCargoDataManager(),
                            null
                    );
                }
        );
    }

    @Test
    public void testLoadCargoSuccessfully() {
        transportationDataContainer.getCargoDataManager()
                .add(new Cargo(new char[][]{{'1'}}));
        transportationDataContainer.getTransportDataManager()
                .add(new Transport());
        LoadingCargoAlgorithm algorithm = AlgorithmTypes.EL.getAlgorithm();
        algorithm.execute(
                transportationDataContainer.getCargoDataManager(),
                transportationDataContainer.getTransportDataManager()
        );
        Transport transport = transportationDataContainer
                .getTransportDataManager().getData().get(0);
        List<Cargo> testList = new ArrayList<>();
        testList.add(new Cargo(new char[][]{{'1'}}));
        assertArrayEquals(testList.get(0).getForm(),
                transportationDataContainer.getTransportDataManager()
                        .getCargos(transport).get(0).getForm()
        );
        assertEquals(1,
                transportationDataContainer.getTransportDataManager()
                        .getCargoWeightInTransport(transport)
        );
    }

    @Test
    public void testNoPlaceFoundForCargo() {
        transportationDataContainer.getCargoDataManager()
                .add(new Cargo(new char[][]{{'2'}, {'2'}})); // 2x1 cargo
        transportationDataContainer.getTransportDataManager()
                .add(new Transport(new char[][]{{' ', '1'}})); // truck with no space

        LoadingCargoAlgorithm algorithm = AlgorithmTypes.EL.getAlgorithm();
        assertThrows(
                NoPlaceException.class,
                () -> {
                    algorithm.execute(
                            transportationDataContainer.getCargoDataManager(),
                            transportationDataContainer.getTransportDataManager()
                    );
                }
        );
    }
}