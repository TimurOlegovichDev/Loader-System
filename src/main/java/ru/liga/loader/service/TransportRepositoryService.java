package ru.liga.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportJsonStructure;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.util.CargoCounter;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransportRepositoryService {

    private final TransportCrudRepository transportRepository;
    private final TransportService transportService;
    private final JsonService jsonService;
    private final CargoCounter cargoCounter;

    @Autowired
    public TransportRepositoryService(TransportCrudRepository transportRepository,
                                      TransportService transportService,
                                      JsonService jsonService,
                                      CargoCounter cargoCounter) {
        this.transportRepository = transportRepository;
        this.transportService = transportService;
        this.jsonService = jsonService;
        this.cargoCounter = cargoCounter;
    }

    /**
     * Сохраняет транспортные средства в JSON-файл по указанному пути.
     *
     * @param filePath путь к JSON-файлу
     */

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

    /**
     * Возвращает информацию о всех транспортных средствах.
     *
     * @return информация о всех транспортных средствах
     */

    public String getTransportsInfo() {
        StringBuilder stringBuilder = new StringBuilder("Отображение транспорта:");
        if (!transportRepository.getKeys().isEmpty()) {
            stringBuilder.append(System.lineSeparator())
                    .append(transportRepository);
        }
        return stringBuilder.toString();
    }

    /**
     * Возвращает информацию о транспортном средстве с указанным идентификатором.
     *
     * @param id идентификатор транспортного средства
     * @return информация о транспортном средстве, если найдено, иначе сообщение о том, что транспортное средство не найдено
     */

    public String getTransportInfoById(String id) {
        List<Cargo> list = new ArrayList<>();
        transportService.getTransportById(id).ifPresent(
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

    /**
     * Удаляет транспортное средство с указанным идентификатором.
     *
     * @param id идентификатор транспортного средства
     * @return сообщение о результате удаления транспортного средства
     */

    public String delete(String id) {
        return transportService.getTransportById(id)
                .map(transport -> {
                    transportRepository.delete(transport);
                    return "Транспорт с идентификатором " + id + " удален успешно!";
                })
                .orElse("Транспорт с идентификатором " + id + " не найден!");
    }
}