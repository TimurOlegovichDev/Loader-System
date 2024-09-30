package ru.liga.loader.factory.transport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.loader.model.entity.Transport;

@Component
@Slf4j
public class TruckFactory implements TransportFactory {

    private final int DEFAULT_TRANSPORT_WIDTH = 6;
    private final int DEFAULT_TRANSPORT_HEIGHT = 6;

    /**
     * Создает грузовой автомобиль по умолчанию.
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
     * Создает грузовой автомобиль по заданной форме кузова.
     *
     * @param body форма кузова грузового автомобиля
     * @return созданный грузовой автомобиль
     */

    @Override
    public Transport createTransport(char[][] body) {
        return new Transport(body);
    }
}
