package ru.liga.loadersystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.repository.CargoCrudRepository;
import ru.liga.loadersystem.repository.TransportCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TransportService {

    private final TransportCrudRepository transportRepository;
    private final CargoCrudRepository cargoCrudRepository;

    /**
     * Возвращает процент занятости транспортного средства.
     *
     * @param transport транспортное средство
     * @return процент занятости транспортного средства
     */
    public int percentageOfOccupancy(Transport transport) {
        int bodyArea = transport.getCharBody().length * transport.getCharBody()[0].length;
        int cargoArea = 0;
        List<Cargo> cargos = cargoCrudRepository.findAllByTransportId(transport.getId());
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
    public Optional<Transport> getTransportById(UUID id) {
        return transportRepository.findById(id);
    }
}
