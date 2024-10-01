package ru.liga.loader.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.loader.exception.InvalidCargoSize;
import ru.liga.loader.exception.NoPlaceException;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;

@Slf4j
@Service
public class DefaultCargoLoaderService implements CargoLoaderService {

    private static char[][] mirrorVertically(char[][] array) {
        char[][] mirroredArray = new char[array.length][];
        for (int i = 0; i < array.length; i++) {
            mirroredArray[array.length - 1 - i] = array[i].clone();
        }
        return mirroredArray;
    }

    /**
     * Загружает указанный груз в транспорт.
     * Этот метод пытается найти пустое место в транспортном средстве для загрузки груза.
     * Он проходит по телу транспортного средства, проверяя каждую ячейку, чтобы увидеть,
     * может ли она вместить груз. Если подходящее место найдено, груз загружается и метод возвращает управление..
     *
     * @param cargo     груз, который будет загружен
     * @param transport транспортное средство, в которое будет загружен груз
     * @throws NoPlaceException если не найдено пустое место в транспортном средстве для груза
     */

    @Override
    public void load(Cargo cargo, Transport transport) {
        log.trace("Попытка найти пустое места для погрузки груза: {}", cargo);
        for (int i = transport.getBody().length - 1; i >= 0; i--) {
            for (int j = 0; j < transport.getBody()[i].length; j++) {
                if (isCargoPlacementPossible(i, j, cargo, transport)) {
                    log.trace("Найдено место для погрузки {}", cargo);
                    placeCargoInTransport(cargo, transport, i, j);
                    return;
                }
            }
        }
        throw new NoPlaceException("Пустое место не найдено для данного груза: " + cargo);
    }

    private boolean isCargoPlacementPossible(int heightIndex, int widthIndex, Cargo cargo, Transport transport) {
        log.trace("Проверка на возможность погрузки груза по координатам ({}, {})", heightIndex, widthIndex);
        if (!canBeLoaded(cargo, transport)) {
            throw new InvalidCargoSize("Этот груз слишком велик: " + cargo);
        }
        char[][] cpBody = copyBody(transport.getBody());
        int height = heightIndex;
        log.debug(cargo.toString());
        for (char[] boxLine : mirrorVertically(cargo.getForm())) {
            int width = widthIndex;
            for (Character character : boxLine) {
                try {
                    if (character == ' ') {
                        continue;
                    }
                    if (cpBody[height][width] != ' ') {
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

    private void placeCargoInTransport(Cargo cargo, Transport transport, int heightIndex, int widthIndex) {
        log.debug("Выполняется погрузка груза: {} по координатам {},{}", cargo, heightIndex, widthIndex);
        int i = heightIndex;
        char[][] body = transport.getBody();
        for (char[] boxLine : mirrorVertically(cargo.getForm())) {
            int j = widthIndex;
            for (Character character : boxLine) {
                body[i][j] = character;
                j++;
            }
            i--;
        }
        log.debug("Груз погружен в транспорт");
    }

    private boolean canBeLoaded(Cargo cargo, Transport transport) {
        return cargo.getHeight() <= transport.getBody().length + 1
                && cargo.getWidth() <= transport.getBody()[0].length + 1;
    }

    private char[][] copyBody(char[][] body) {
        char[][] newBody = new char[body.length][body[0].length];
        for (int i = 0; i < body.length; i++) {
            System.arraycopy(body[i], 0, newBody[i], 0, body[i].length);
        }
        return newBody;
    }
}
