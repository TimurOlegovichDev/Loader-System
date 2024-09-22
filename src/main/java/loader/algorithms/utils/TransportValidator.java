package loader.algorithms.utils;

import loader.db.TransportDataManager;
import loader.exceptions.NoPlaceException;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;

public interface TransportValidator {

    void validateTransportData(TransportDataManager transportDataManager) throws NoPlaceException;

    boolean canInsertInTransport(int indexHeight, int indexWidth, Cargo cargo, Transport transport);
}