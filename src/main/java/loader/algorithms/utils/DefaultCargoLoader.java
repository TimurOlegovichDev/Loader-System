package loader.algorithms.utils;

import loader.exceptions.InvalidCargoSize;
import loader.exceptions.NoPlaceException;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultCargoLoader implements CargoLoader {

    private final TransportValidator transportValidator;

    public DefaultCargoLoader(TransportValidator transportValidator) {
        this.transportValidator = transportValidator;
    }

    @Override
    public boolean canLoadCargo(Cargo cargo, Transport transport) {
        return cargo.getWidth() <= transport.getBody().length
                && cargo.getHeight() <= transport.getBody()[0].length;
    }

    @Override
    public void tryLoadToTransport(Cargo cargo, Transport transport) {
        log.trace("Trying to find empty space to load cargo: {}", cargo);
        if (!canLoadCargo(cargo, transport)) {
            throw new InvalidCargoSize("This cargo is too big for this transport: " + cargo);
        }
        for (int i = transport.getBody().length - 1; i >= 0; i--) {
            for (int j = 0; j < transport.getBody()[i].length; j++) {
                if (transportValidator.canInsertInTransport(i, j, cargo, transport)) {
                    log.trace("Found empty space to load cargo: {}", cargo);
                    loadCargo(cargo, transport, i, j);
                    return;
                }
            }
        }
        throw new NoPlaceException("No empty space found to load cargo: " + cargo);
    }


    @Override
    public void loadCargo(Cargo cargo, Transport transport, int heightIndex, int widthIndex) {
        int i = heightIndex;
        char[][] body = transport.getBody();
        for (char[] boxLine : cargo.getForm()) {
            int j = widthIndex;
            for (Character character : boxLine) {
                body[i][j] = character;
                j++;
            }
            i--;
        }
    }
}
