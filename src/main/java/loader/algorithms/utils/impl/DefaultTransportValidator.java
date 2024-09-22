package loader.algorithms.utils.impl;

import loader.algorithms.utils.TransportValidator;
import loader.db.TransportDataManager;
import loader.exceptions.NoPlaceException;
import loader.model.entites.Cargo;
import loader.model.entites.Transport;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultTransportValidator implements TransportValidator {

    @Override
    public void validateTransportData(TransportDataManager transportDataManager) throws NoPlaceException {
        if (transportDataManager == null || transportDataManager.getData().isEmpty()) {
            throw new NoPlaceException("Транспорт для погрузки отсутствует");
        }
    }

    @Override
    public boolean canInsertInTransport(int heightIndex,
                                        int widthIndex,
                                        Cargo cargo,
                                        Transport transport) {
        log.trace("Проверка на возможность погрузки груза по координатам ({}, {})", heightIndex, widthIndex);
        char[][] cpBody = copyBody(transport.getBody());
        int height = heightIndex;
        log.debug(cargo.toString());
        for (char[] boxLine : cargo.getForm()) {
            int width = widthIndex;
            for (Character character : boxLine) {
                try {
                    if (cpBody[height][width] == ' ') {
                        cpBody[height][width] = character;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    log.trace("Груз невозможно загрузить по координатам ({}, {}), нет свободного места", height, width);
                    return false;
                }
                width++;
            }
            height--;
        }
        log.trace("Груз может быть загружен");
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
