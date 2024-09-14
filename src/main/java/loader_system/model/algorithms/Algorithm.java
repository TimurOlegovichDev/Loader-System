package loader_system.model.algorithms;

import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.entites.cargos.Cargo;
import loader_system.model.entites.transports.Transport;
import loader_system.model.exceptions.NoPlaceException;

public abstract class Algorithm {

    public abstract void execute(CargoData cargoData, TransportData transportData);

    protected void tryFindEmptySpaceAndLoad(Cargo cargo, Transport transport) throws NoPlaceException {
        for (int i = transport.getBody().length - 1; i >= 0; i--) {
            for (int j = 0; j < transport.getBody()[i].length; j++) {
                if (canInsertInTransport(i, j, cargo, transport)) {
                    transport.loadCargo(cargo, i, j);
                    return;
                }
            }
        }
        throw new NoPlaceException();
    }

    private boolean canInsertInTransport(int indexBodyHeight, int indexBodyWidth, Cargo cargo, Transport transport) {
        char[][] cpBody = transport.getBody();
        int i = indexBodyHeight;
        for (char[] boxLine : cargo.getForm()) {
            int j = indexBodyWidth;
            for (Character character : boxLine) {
                try {
                    if (cpBody[i][j] == ' ') {
                        cpBody[i][j] = character;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
                j++;
            }
            i--;
        }
        return true;
    }

}
