package ru.liga.loader.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.exception.NoPlaceException;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class DefaultCargoLoaderTest {

    private final DefaultCargoLoader cargoLoader = new DefaultCargoLoader();

    @Test
    void testLoad_ThrowsNoPlaceException() {
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A'}});
        Transport transport = new Transport("transport1", new char[][]{{'A', 'A'}, {'A', 'A'}});
        assertThrows(NoPlaceException.class, () -> cargoLoader.load(cargo, transport));
    }

    @Test
    void testLoad_ThrowsInvalidCargoSize() {
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A', 'A', 'A'}, {'A', 'A'}});
        Transport transport = new Transport(2, 2);
        assertThrows(NoPlaceException.class, () -> cargoLoader.load(cargo, transport));
    }

    @Test
    void testLoad_Successfully() {
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A', 'A'}, {'A', 'A'}});
        Transport transport = new Transport(2, 2);
        assertDoesNotThrow(() -> cargoLoader.load(cargo, transport));
    }
}