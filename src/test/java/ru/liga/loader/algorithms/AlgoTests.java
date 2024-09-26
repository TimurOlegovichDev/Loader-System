package ru.liga.loader.algorithms;

import org.junit.jupiter.api.Test;
import ru.liga.loader.algorithm.LoadingCargoAlgorithm;
import ru.liga.loader.db.CargoDataManager;
import ru.liga.loader.db.TransportDataManager;
import ru.liga.loader.enums.AlgorithmTypes;
import ru.liga.loader.exception.NoPlaceException;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlgoTests {

    private final TransportDataManager transportDataManager = new TransportDataManager(new HashMap<>());
    private final CargoDataManager cargoDataManager = new CargoDataManager(new ArrayList<>());

    @Test
    public void testLoadCargoSuccessfully() {
        cargoDataManager.add(new Cargo(new char[][]{{'1'}}));
        transportDataManager.add(new Transport());
        LoadingCargoAlgorithm algorithm =
                AlgorithmTypes.createElAlgorithm(
                        transportDataManager,
                        cargoDataManager
                );
        algorithm.execute();
        Transport transport = transportDataManager.getData().get(0);
        List<Cargo> testList = new ArrayList<>();
        testList.add(new Cargo(new char[][]{{'1'}}));
        assertArrayEquals(
                testList.get(0).getForm(),
                transportDataManager.getCargos(transport).get(0).getForm()
        );
        assertEquals(
                1,
                transportDataManager.getCargoWeightInTransport(transport)
        );
    }

    @Test
    public void testNoPlaceFoundForCargo() {
        cargoDataManager.add(new Cargo(new char[][]{{'2'}, {'2'}}));
        transportDataManager.add(new Transport(new char[][]{{' ', '1'}}));
        LoadingCargoAlgorithm algorithm = AlgorithmTypes.createElAlgorithm(transportDataManager, cargoDataManager);
        assertThrows(
                NoPlaceException.class,
                algorithm::execute
        );
    }
}