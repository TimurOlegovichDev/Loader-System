package ru.liga.loader.initializers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loader.factory.transport.TransportFactory;
import ru.liga.loader.factory.transport.TruckFactory;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportJsonStructure;
import ru.liga.loader.model.structure.TransportSizeStructure;
import ru.liga.loader.service.JsonService;

import java.io.InputStream;
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
     * Инициализирует грузовые автомобили по заданным размерам.
     *
     * @param sizeStructures список размеров для инициализации
     * @return список грузовых автомобилей
     */

    public List<Transport> initialize(List<TransportSizeStructure> sizeStructures) {
        TransportFactory transportFactory = new TruckFactory();
        List<Transport> transports = new ArrayList<>();
        for (TransportSizeStructure sizeStructure : sizeStructures) {
            transports.add(
                    transportFactory.createTransport(
                            sizeStructure.width(),
                            sizeStructure.height()
                    )
            );
        }
        return transports;
    }

    /**
     * Инициализирует грузовые автомобили из файла JSON.
     * Этот метод инициализирует грузовые автомобили из файла JSON и возвращает мапу грузовых автомобилей и грузов.
     *
     * @param stream файл JSON
     * @return карта грузовых автомобилей и грузов
     */

    public Map<Transport, List<Cargo>> initializeFromJson(InputStream stream) {
        List<TransportJsonStructure> transportJsonStructures =
                jsonService.read(TransportJsonStructure.class, stream);
        Map<Transport, List<Cargo>> map = new HashMap<>();
        for (TransportJsonStructure transportJsonStructure : transportJsonStructures) {
            map.put(
                    new TruckFactory().createTransport(
                            transportJsonStructure
                    ),
                    new ArrayList<>(
                            transportJsonStructure.cargos()
                    )
            );
        }
        return map;
    }
}
