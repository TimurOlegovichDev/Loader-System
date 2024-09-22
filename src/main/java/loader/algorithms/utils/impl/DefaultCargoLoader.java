package loader.algorithms.utils.impl;

import loader.algorithms.utils.CargoLoader;
import loader.algorithms.utils.TransportValidator;
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
    public void tryLoadToTransport(Cargo cargo, Transport transport) {
        log.trace("Попытка найти пустое места для погрузки груза: {}", cargo);
        if (!canLoadCargo(cargo, transport)) {
            throw new InvalidCargoSize("Этот груз слишком велик: " + cargo);
        }
        for (int i = transport.getBody().length - 1; i >= 0; i--) {
            for (int j = 0; j < transport.getBody()[i].length; j++) {
                if (transportValidator.canInsertInTransport(i, j, cargo, transport)) {
                    log.trace("Найдено пустое место для погрузки: {}", cargo);
                    loadCargo(cargo, transport, i, j);
                    return;
                }
            }
        }
        throw new NoPlaceException("Пустое место не найдено для данного груза: " + cargo);
    }

    @Override
    public boolean canLoadCargo(Cargo cargo, Transport transport) {
        return cargo.getWidth() <= transport.getBody().length
                && cargo.getHeight() <= transport.getBody()[0].length;
    }

    @Override
    public void loadCargo(Cargo cargo, Transport transport, int heightIndex, int widthIndex) {
        log.debug("Выполняется погрузка груза: {} по координатам {},{}", cargo, heightIndex, widthIndex);
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
        log.debug("Груз погружен в транспорт");
    }
}
