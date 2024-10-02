package ru.liga.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportJsonStructure;
import ru.liga.loader.repository.CargoCrudRepository;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.util.CargoCounter;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransportService {

    private final TransportCrudRepository transportRepository;
    private final CargoCrudRepository cargoRepository;
    private final JsonService jsonService;
    private final LoadingService loadingService;
    private final CargoCounter cargoCounter;

    @Autowired
    public TransportService(TransportCrudRepository transportRepository,
                            CargoCrudRepository cargoRepository,
                            JsonService jsonService, LoadingService loadingService, CargoCounter cargoCounter) {
        this.transportRepository = transportRepository;
        this.cargoRepository = cargoRepository;
        this.jsonService = jsonService;
        this.loadingService = loadingService;
        this.cargoCounter = cargoCounter;
    }

    public void saveToJson(String filePath) {
        List<TransportJsonStructure> structures = new ArrayList<>();
        for (Transport transport : transportRepository.getKeys()) {
            structures.add(
                    new TransportJsonStructure(
                            transport.getId(),
                            transport.getBody(),
                            transportRepository.getCargos(transport)
                    )
            );
        }
        jsonService.writeObject(structures, filePath);
    }

    public String getTransportsInfo() {
        StringBuilder stringBuilder = new StringBuilder("Отображение транспорта:");
        if (!transportRepository.getKeys().isEmpty()) {
            stringBuilder.append(System.lineSeparator())
                    .append(transportRepository);
        }
        return stringBuilder.toString();
    }

    public String getTransportInfoById(String id) {
        List<Cargo> list = new ArrayList<>();
        transportRepository.getTransportById(id).ifPresent(
                transport -> list.addAll(transportRepository.getCargos(transport)));
        StringBuilder stringBuilder = new StringBuilder("Информация о транспорте").append(System.lineSeparator());
        if (list.isEmpty()) {
            return stringBuilder.append("Транспорт пустой").toString();
        }
        cargoCounter.count(list)
                .forEach((name, count) ->
                        stringBuilder.append("Название груза: ")
                                .append(name)
                                .append(", количество: ")
                                .append(count)
                                .append(System.lineSeparator())
                );
        return stringBuilder.toString();
    }

    public String delete(String id) {
        return transportRepository.getTransportById(id)
                .map(transport -> {
                    transportRepository.delete(transport);
                    return "Транспорт с идентификатором " + id + " удален успешно!";
                })
                .orElse("Транспорт с идентификатором " + id + " не найден!");
    }
}