package ru.liga.loadersystem.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.liga.loadersystem.algorithm.util.CargoSorter;
import ru.liga.loadersystem.algorithm.util.TransportSorter;
import ru.liga.loadersystem.exception.NoPlaceException;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.repository.CargoCrudRepository;
import ru.liga.loadersystem.repository.TransportCrudRepository;
import ru.liga.loadersystem.util.CargoLoader;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class EvenLoadingAlgorithm implements LoadingCargoAlgorithm {

    private final CargoSorter cargoSorter;
    private final TransportSorter transportSorter;
    private final TransportCrudRepository transportDataRepository;
    private final CargoLoader cargoLoader;
    private final CargoCrudRepository cargoCrudRepository;

    @Autowired
    public EvenLoadingAlgorithm(CargoSorter cargoSorter,
                                @Qualifier("transportSorterByOccupiedAreaAsc") TransportSorter transportSorter,
                                TransportCrudRepository transportDataRepository,
                                CargoLoader cargoLoader,
                                CargoCrudRepository cargoCrudRepository) {
        this.cargoSorter = cargoSorter;
        this.transportSorter = transportSorter;
        this.transportDataRepository = transportDataRepository;
        this.cargoLoader = cargoLoader;
        this.cargoCrudRepository = cargoCrudRepository;
    }

    @Override
    public void execute(List<Cargo> cargos, List<Transport> transports) {
        log.info("Старт алгоритма равномерной погрузки");
        for (Cargo cargo : cargoSorter.sort(cargos)) {
            log.info("Погрузка груза: {}", cargo);
            try {
                loadCargo(cargo, transports);
                log.debug("Груз успешно погружен: {}", cargo);
            } catch (NoPlaceException e) {
                log.warn(e.getMessage());
            }
        }
        log.info("Равномерная погрузка окончена");
    }

    private void loadCargo(Cargo cargo, List<Transport> transports) throws NoPlaceException {
        Optional<Transport> optional = findMostFreeTransport(cargo, transports);
        optional.ifPresentOrElse(
                transport -> {
                    cargoLoader.load(cargo, transport);
                    Cargo copy = new Cargo(cargo.getName(), cargo.getForm());
                    copy.setTransportId(transport.getId());
                    cargoCrudRepository.save(copy);
                    transportDataRepository.save(transport);
                    log.info("Груз успешно загружен: {}", cargo);
                },
                () -> {
                    throw new NoPlaceException("Нет грузовика для погрузки груза: " + cargo);
                }
        );
    }

    private Optional<Transport> findMostFreeTransport(Cargo cargo, List<Transport> transports) {
        log.debug("Поиск максимально незагруженного транспорта");
        return getFirstCanToLoadTransport(
                transportSorter.sort(transportDataRepository, transports),
                cargo
        );
    }

    private Optional<Transport> getFirstCanToLoadTransport(List<Transport> transports, Cargo cargo) {
        for (Transport transport : transports) {
            if (transport.canBeLoaded(cargo)) {
                return Optional.of(transport);
            }
        }
        return Optional.empty();
    }
}