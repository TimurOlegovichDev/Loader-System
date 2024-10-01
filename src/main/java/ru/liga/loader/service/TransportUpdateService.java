package ru.liga.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.enums.AlgorithmTypes;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.processor.LoadingProcessor;
import ru.liga.loader.repository.TransportCrudRepository;

import java.util.List;

@Service
public class TransportUpdateService {

    private final LoadingProcessor processor;
    private final TransportCrudRepository transportCrudRepository;

    @Autowired
    public TransportUpdateService(LoadingProcessor processor, TransportCrudRepository transportCrudRepository) {
        this.processor = processor;
        this.transportCrudRepository = transportCrudRepository;
    }

    public void update(List<Cargo> cargoList) {
        clearTransports();
        processor.process(AlgorithmTypes.createMesAlgorithm(
                transportCrudRepository,
                cargoList
        ));
    }

    private void clearTransports() {
        for (Transport transport : transportCrudRepository.getKeys()) {
            transport.unloadAll();
            transportCrudRepository.removeAllCargo(transport);
        }
    }
}
