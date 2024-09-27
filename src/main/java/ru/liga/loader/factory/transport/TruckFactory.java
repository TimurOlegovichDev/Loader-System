package ru.liga.loader.factory.transport;

import ru.liga.loader.model.entity.Transport;

public class TruckFactory implements TransportFactory {

    /**
     * Создает грузовой автомобиль по умолчанию.
     *
     * @return созданный грузовой автомобиль
     */

    @Override
    public Transport createTransport() {
        return new Transport(6, 6);
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
