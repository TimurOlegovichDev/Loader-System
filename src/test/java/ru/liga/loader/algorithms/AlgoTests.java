package ru.liga.loader.algorithms;

import org.junit.jupiter.api.Test;
import ru.liga.loader.algorithm.LoadingCargoAlgorithm;
import ru.liga.loader.enums.AlgorithmTypes;
import ru.liga.loader.exception.NoPlaceException;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.CargoDataRepository;
import ru.liga.loader.repository.TransportDataRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlgoTests {

    private final TransportDataRepository transportDataRepository = new TransportDataRepository(new HashMap<>());
    private final CargoDataRepository cargoDataRepository = new CargoDataRepository(new HashMap<>());

    @Test
    public void testLoadCargoSuccessfully() {
        cargoDataRepository.add(new Cargo("", new char[][]{{'1'}}));
        transportDataRepository.add(new Transport(6, 6));
        LoadingCargoAlgorithm algorithm =
                AlgorithmTypes.createElAlgorithm(
                        transportDataRepository,
                        cargoDataRepository
                );
        algorithm.execute();
        Transport transport = transportDataRepository.getData().get(0);
        List<Cargo> testList = new ArrayList<>();
        testList.add(new Cargo("", new char[][]{{'1'}}));
        assertArrayEquals(
                testList.get(0).getForm(),
                transportDataRepository.getCargos(transport).get(0).getForm()
        );
        assertEquals(
                1,
                transportDataRepository.getCargoWeightInTransport(transport)
        );
    }

    @Test
    public void testNoPlaceFoundForCargo() {
        cargoDataRepository.add(new Cargo("", new char[][]{{'2'}, {'2'}}));
        transportDataRepository.add(new Transport(new char[][]{{' ', '1'}}));
        LoadingCargoAlgorithm algorithm = AlgorithmTypes.createElAlgorithm(transportDataRepository, cargoDataRepository);
        assertThrows(
                NoPlaceException.class,
                algorithm::execute
        );
    }
}