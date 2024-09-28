package ru.liga.loader.util.initializers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loader.factory.transport.TransportFactory;
import ru.liga.loader.factory.transport.TruckFactory;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportJsonStructure;
import ru.liga.loader.service.JsonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TruckInitializer {

    private final JsonService jsonService;

    @Autowired
    public TruckInitializer(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    /**
     * Инициализирует грузовые автомобили по количеству.
     * Этот метод инициализирует грузовые автомобили по введеному количеству и возвращает список грузовых автомобилей.
     *
     * @param numberOfTransport количество грузовых автомобилей
     * @return список грузовых автомобилей
     */

    public List<Transport> initialize(int numberOfTransport) {
        TransportFactory transportFactory = new TruckFactory();
        List<Transport> transports = new ArrayList<>();
        for (int i = 0; i < numberOfTransport; i++) {
            transports.add(
                    transportFactory.createTransport()
            );
        }
        return transports;
    }

    /**
     * Инициализирует грузовые автомобили из файла JSON.
     * Этот метод инициализирует грузовые автомобили из файла JSON и возвращает мапу грузовых автомобилей и грузов.
     *
     * @param filepath путь к файлу JSON
     * @return карта грузовых автомобилей и грузов
     */

    public Map<Transport, List<Cargo>> initializeFromJson(String filepath) {
        List<TransportJsonStructure> transportJsonStructures =
                jsonService.read(TransportJsonStructure.class, filepath);
        Map<Transport, List<Cargo>> map = new HashMap<>();
        for (TransportJsonStructure transportJsonStructure : transportJsonStructures) {
            map.put(
                    new TruckFactory().createTransport(
                            transportJsonStructure.getBody()
                    ),
                    new ArrayList<>(
                            transportJsonStructure.getCargos()
                    )
            );
        }
        return map;
    }
}
