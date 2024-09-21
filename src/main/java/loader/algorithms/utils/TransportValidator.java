package loader.algorithms.utils;

import loader.db.TransportData;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;

public interface TransportValidator {

    void validateTransportData(TransportData transportData);

    boolean canInsertInTransport(int indexHeight, int indexWidth, Cargo cargo, Transport transport);
}