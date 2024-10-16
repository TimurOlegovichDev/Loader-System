package ru.liga.loadersystem.initializers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.loadersystem.factory.transport.TruckFactory;
import ru.liga.loadersystem.model.dto.TransportDto;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.model.json.TransportJsonStructure;
import ru.liga.loadersystem.service.JsonService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TruckInitializer {

    private final JsonService jsonService;
    private final TruckFactory truckFactory;

    /**
     * Инициализирует грузовые автомобили по заданным размерам.
     *
     * @param sizeStructures список размеров для инициализации
     * @return список грузовых автомобилей
     */
    public List<Transport> initialize(List<TransportDto> sizeStructures) {
        List<Transport> transports = new ArrayList<>();
        for (TransportDto sizeStructure : sizeStructures) {
            transports.add(
                    truckFactory.createTransport(
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

    public Map<Transport, List<Cargo>> initializeFromJson(InputStream stream) throws Exception {
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
