package ru.liga.loadersystem.factory.transport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.model.json.TransportJsonStructure;

import java.util.UUID;

@Component
@Slf4j
public class TruckFactory implements TransportFactory {

    private final int DEFAULT_TRANSPORT_WIDTH = 6;
    private final int DEFAULT_TRANSPORT_HEIGHT = 6;

    /**
     * Создает грузовой автомобиль с размерами по умолчанию.
     *
     * @return созданный грузовой автомобиль
     */

    @Override
    public Transport createTransport() {
        return new Transport(
                DEFAULT_TRANSPORT_WIDTH,
                DEFAULT_TRANSPORT_HEIGHT
        );
    }

    /**
     * Создает грузовой автомобиль с указанным размером.
     *
     * @return созданный грузовой автомобиль
     */

    @Override
    public Transport createTransport(int width, int height) {
        if (width <= 0 || height <= 0) {
            log.warn("Неверные размеры кузова, размеры восстановлены до начальных значений (6,6)");
            return createTransport();
        }
        return new Transport(width, height);
    }

    /**
     * Создает грузовой автомобиль по заданной форме кузова с указаным идентификатором.
     *
     * @param body форма кузова грузового автомобиля
     * @return созданный грузовой автомобиль
     */

    @Override
    public Transport createTransport(String id, String body) {
        return new Transport(UUID.fromString(id), body);
    }

    /**
     * Создает грузовой автомобиль по структуре, полученной из json файла
     *
     * @param transportJsonStructure структура, хранящая в себе необходимые данные
     * @return созданный грузовой автомобиль
     */

    @Override
    public Transport createTransport(TransportJsonStructure transportJsonStructure) {
        return new Transport(transportJsonStructure);
    }
}
