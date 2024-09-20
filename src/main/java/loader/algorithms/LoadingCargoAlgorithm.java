package loader.algorithms;

import loader.db.CargoData;
import loader.db.TransportData;
import loader.model.entites.cargos.Cargo;
import loader.model.entites.transports.Transport;
import loader.exceptions.InvalidCargoSize;
import loader.exceptions.NoPlaceException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public abstract class LoadingCargoAlgorithm {

    public abstract void execute(CargoData cargoData, TransportData transportData);

    protected void tryLoadToTransport(Cargo cargo, Transport transport) throws NoPlaceException {
        log.trace("Trying to find empty space to load cargo: {}", cargo);
        if(!canLoadCargo(cargo, transport)) {
            throw new InvalidCargoSize("This cargo is too big for this transport: " + cargo);
        }
        for (int i = transport.getBody().length - 1; i >= 0; i--) {
            for (int j = 0; j < transport.getBody()[i].length; j++) {
                if (canInsertInTransport(i, j, cargo, transport)) {
                    log.trace("Found empty space to load cargo: {}", cargo);
                    loadCargo(cargo, transport, i, j);
                    return;
                }
            }
        }
        throw new NoPlaceException("No empty space found to load cargo: " + cargo);
    }

    private boolean canInsertInTransport(int heightIndex, int widthIndex, Cargo cargo, Transport transport) {
        log.trace("Checking if cargo can be inserted at ({}, {})", heightIndex, widthIndex);
        char[][] cpBody = transport.getBody();
        int height = heightIndex;
        int width = widthIndex;
        log.debug(cargo.toString());
        for (char[] boxLine : cargo.getForm()) {
            width = 0;
            for (Character character : boxLine) {
                try {
                    if (cpBody[height][width] == ' ') {
                        cpBody[height][width] = character;
                        log.trace("Inserted part of cargo at: {} {}", height, width);
                    } else {
                        log.trace("Cannot insert cargo at ({}, {}), space is occupied", height, width);
                        return false;
                    }
                } catch (Exception e) {
                    log.trace("Cannot insert cargo at ({}, {}), no empty space", height, width);
                    return false;
                }
                width++;
            }
            height--;
        }
        log.trace("Cargo can be inserted at ({}, {})", heightIndex, widthIndex);
        return true;
    }

    protected boolean canLoadCargo(Cargo cargo, Transport transport) {
        return cargo.getWidth() <= transport.getBody().length
                && cargo.getHeight() <= transport.getBody()[0].length;
    }

    protected void validateTransportData(TransportData transportData) {
        if (transportData == null || transportData.getData().isEmpty()) {
            throw new NoPlaceException("There is no truck to load");
        }
    }

    protected List<Cargo> sortCargosByWeight(List<Cargo> cargos) {
        List<Cargo> sortedCargos = new ArrayList<>(cargos);
        sortedCargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        return sortedCargos;
    }

    private void loadCargo(Cargo cargo, Transport transport, int heightIndex, int widthIndex) {
        int i = heightIndex;
        for (char[] boxLine : cargo.getForm()) {
            int j = widthIndex;
            for (Character character : boxLine) {
                transport.getBody()[i][j] = character;
                j++;
            }
            i--;
        }
    }

}