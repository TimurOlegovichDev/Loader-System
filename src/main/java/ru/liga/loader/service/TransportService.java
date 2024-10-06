package ru.liga.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.model.structure.TransportJsonStructure;
import ru.liga.loader.repository.CargoCrudRepository;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.util.CargoCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransportService {

    private final TransportCrudRepository transportRepository;
    private final TransportRepositoryService transportRepositoryService;
    private final JsonService jsonService;
    private final CargoCounter cargoCounter;
    private final CargoCrudRepository cargoCrudRepository;

    @Autowired
    public TransportService(@Qualifier("transportCrudRepository") TransportCrudRepository transportRepository,
                            TransportRepositoryService transportRepositoryService,
                            JsonService jsonService,
                            CargoCounter cargoCounter, @Qualifier("cargoCrudRepository") CargoCrudRepository cargoCrudRepository) {
        this.transportRepository = transportRepository;
        this.transportRepositoryService = transportRepositoryService;
        this.jsonService = jsonService;
        this.cargoCounter = cargoCounter;
        this.cargoCrudRepository = cargoCrudRepository;
    }

    /**
     * Сохраняет транспортные средства в JSON-файл по указанному пути.
     *
     * @param filePath путь к JSON-файлу
     */

    public void saveToJson(String filePath) {
        jsonService.writeObject(getStructures(), filePath);
    }

    public List<TransportJsonStructure> getStructures() {
        List<TransportJsonStructure> structures = new ArrayList<>();
        for (Transport transport : transportRepository.findAll()) {
            structures.add(
                    new TransportJsonStructure(
                            transport.getId(),
                            transport.getBody(),
                            cargoCrudRepository.findAllByTransportId(transport.getId())
                    )
            );
        }
        return structures;
    }

    /**
     * Возвращает информацию о всех транспортных средствах.
     *
     * @return информация о всех транспортных средствах
     */

    public String getTransportsInfo() {
        StringBuilder stringBuilder = new StringBuilder("Отображение транспорта:");
        transportRepository.findAll().forEach(
                transport -> stringBuilder.append(System.lineSeparator())
                        .append(transport.toString())
        );
        return stringBuilder.toString();
    }

    /**
     * Возвращает информацию о транспортном средстве с указанным идентификатором.
     *
     * @param id идентификатор транспортного средства
     * @return информация о транспортном средстве, если найдено, иначе сообщение о том, что транспортное средство не найдено
     */

    public String getTransportInfoById(UUID id) {
        List<Cargo> list = new ArrayList<>();
        transportRepositoryService.getTransportById(id).ifPresent(
                transport -> list.addAll(cargoCrudRepository.findAllByTransportId(transport.getId())));
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

    public String delete(UUID id) {
        return transportRepositoryService.getTransportById(id)
                .map(transport -> {
                    cargoCrudRepository.deleteAllByTransportId(id);
                    transportRepository.delete(transport);
                    return "Транспорт с идентификатором " + id + " удален успешно!";
                })
                .orElse("Транспорт с идентификатором " + id + " не найден!");
    }
}