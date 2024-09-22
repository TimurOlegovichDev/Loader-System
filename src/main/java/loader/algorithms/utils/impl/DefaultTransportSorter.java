package loader.algorithms.utils.impl;

import loader.algorithms.utils.TransportSorter;
import loader.db.TransportDataManager;
import loader.model.entites.Transport;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DefaultTransportSorter implements TransportSorter {
    @Override
    public List<Transport> sort(TransportDataManager transportDataManager) {
        log.debug("Сортировка транспорта по весу груза...");
        return transportDataManager.getData().stream()
                .sorted(Comparator.comparingInt(transportDataManager::getCargoWeightInTransport))
                .collect(Collectors.toList());
    }
}
