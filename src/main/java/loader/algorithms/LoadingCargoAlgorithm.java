package loader.algorithms;

import loader.db.CargoData;
import loader.db.TransportData;
import loader.model.entites.cargos.Cargo;
import loader.model.entites.transports.Transport;
import loader.exceptions.InvalidCargoSize;
import loader.exceptions.NoPlaceException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
                    transport.loadCargo(cargo, i, j);
                    return;
                }
            }
        }
        throw new NoPlaceException("No empty space found to load cargo: " + cargo);
    }

    private boolean canInsertInTransport(int indexBodyHeight, int indexBodyWidth, Cargo cargo, Transport transport) {
        log.trace("Checking if cargo can be inserted at ({}, {})", indexBodyHeight, indexBodyWidth);
        char[][] cpBody = transport.getBody();
        int i = indexBodyHeight;
        for (char[] boxLine : cargo.getForm()) {
            int j = indexBodyWidth;
            for (Character character : boxLine) {
                try {
                    if (cpBody[i][j] == ' ') {
                        cpBody[i][j] = character;
                    } else {
                        log.trace("Cannot insert cargo at ({}, {}), space is occupied", i, j);
                        return false;
                    }
                } catch (Exception e) {
                    log.trace("Cannot insert cargo at ({}, {}), no empty space", i, j);
                    return false;
                }
                j++;
            }
            i--;
        }
        log.trace("Cargo can be inserted at ({}, {})", indexBodyHeight, indexBodyWidth);
        return true;
    }

    protected boolean canLoadCargo(Cargo cargo, Transport transport) {
        return cargo.getWidth() <= transport.getBody()[0].length
                &&
                cargo.getHeight() <= transport.getBody().length;
    }

    protected void validateTransportData(TransportData transportData) {
        if (transportData.getData().isEmpty()) {
            throw new NoPlaceException("There is no truck to load");
        }
    }

    protected List<Cargo> sortCargosByWeight(List<Cargo> cargos) {
        List<Cargo> sortedCargos = new ArrayList<>(cargos);
        sortedCargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        return sortedCargos;
    }

}