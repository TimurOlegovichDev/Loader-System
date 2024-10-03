package ru.liga.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.TransportCrudRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransportRepositoryService {

    private final TransportCrudRepository transportRepository;

    @Autowired
    public TransportRepositoryService(TransportCrudRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    /**
     * Возвращает процент занятости транспортного средства.
     *
     * @param transport транспортное средство
     * @return процент занятости транспортного средства
     */
    public int percentageOfOccupancy(Transport transport) {
        if (!transportRepository.getKeys().contains(transport)) {
            transportRepository.add(transport);
        }
        int bodyArea = transport.getBody().length * transport.getBody()[0].length;
        int cargoArea = 0;
        List<Cargo> cargos = transportRepository.getCargos(transport);
        if (cargos == null || bodyArea <= 0) {
            return cargoArea;
        }
        for (Cargo cargoInTransport : cargos) {
            cargoArea += cargoInTransport.getArea();
        }
        return getPercent(cargoArea, bodyArea);
    }

    private int getPercent(int cargoArea, int bodyArea) {
        return cargoArea * 100 / bodyArea;
    }

    /**
     * Возвращает транспортное средство по его идентификатору.
     *
     * @param id идентификатор транспортного средства
     * @return транспортное средство, если найдено, иначе Optional.empty()
     */
    public Optional<Transport> getTransportById(String id) {
        for (Transport transport : transportRepository.getKeys()) {
            if (transport.getId().equals(id)) {
                return Optional.of(transport);
            }
        }
        return Optional.empty();
    }

    /**
     * Обновляет имена грузов в репозитории.
     *
     * @param lastName  старое имя груза
     * @param cargoName новое имя груза
     */
    public void updateCargosName(String lastName, String cargoName) {
        for (Transport transport : transportRepository.getKeys()) {
            for (Cargo cargoInTransport : transportRepository.getCargos(transport)) {
                if (cargoInTransport.getName().equals(lastName)) {
                    cargoInTransport.setName(cargoName);
                }
            }
        }
    }
}
