package ru.liga.loader.algorithms;

import org.junit.jupiter.api.Test;
import ru.liga.loader.algorithm.LoadingCargoAlgorithm;
import ru.liga.loader.enums.AlgorithmTypes;
import ru.liga.loader.exception.NoPlaceException;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.impl.DefaultCrudCargoRepository;
import ru.liga.loader.repository.impl.DefaultCrudTransportRepository;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AlgoTests {

    private final DefaultCrudTransportRepository transportDataRepository = new DefaultCrudTransportRepository(new HashMap<>());
    private final DefaultCrudCargoRepository cargoDataRepository = new DefaultCrudCargoRepository(new HashMap<>());

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