package loader.algorithms.utils.impl;

import loader.algorithms.utils.TransportValidator;
import loader.db.TransportData;
import loader.exceptions.NoPlaceException;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultTransportValidator implements TransportValidator {

    @Override
    public void validateTransportData(TransportData transportData) throws NoPlaceException {
        if (transportData == null || transportData.getData().isEmpty()) {
            throw new NoPlaceException("There is no truck to load");
        }
    }

    @Override
    public boolean canInsertInTransport(int heightIndex, int widthIndex, Cargo cargo, Transport transport) {
        log.trace("Checking if cargo can be inserted at ({}, {})", heightIndex, widthIndex);
        char[][] cpBody = copyBody(transport.getBody());
        int height = heightIndex;
        log.debug(cargo.toString());
        for (char[] boxLine : cargo.getForm()) {
            int width = widthIndex;
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

    private char[][] copyBody(char[][] body) {
        char[][] newBody = new char[body.length][body[0].length];
        for (int i = 0; i < body.length; i++) {
            System.arraycopy(body[i], 0, newBody[i], 0, body[i].length);
        }
        return newBody;
    }
}
