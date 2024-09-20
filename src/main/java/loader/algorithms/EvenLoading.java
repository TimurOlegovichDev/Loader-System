package loader.algorithms;

import loader.db.CargoData;
import loader.db.TransportData;
import loader.entites.cargos.Cargo;
import loader.entites.transports.Transport;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EvenLoading extends LoadingCargoAlgorithm {

    @Override
    public void execute(CargoData cargoData, TransportData transportData) {
        log.debug("Executing EvenLoading algorithm");
        validateTransportData(transportData);
        List<Cargo> cargos = sortCargosByWeight(cargoData.getData());
        for (Cargo cargo : cargos) {
            log.info("Processing cargo: {}", cargo);
            try {
                Transport transport = chooseTruckToLoad(transportData);
                tryLoadToTransport(cargo, transport);
                transportData.addCargoInTransport(transport, cargo);
                log.info("Load cargo completed: {}", cargo);
            } catch (Exception e) {
                log.warn(e.getMessage());
                return;
            }
        }
        log.debug("EvenLoading algorithm finished");
    }

    private Transport chooseTruckToLoad(TransportData transportData) {
        List<Transport> transports = transportData.getData();
        Transport truck = transports.get(0);
        for (int i = 1; i < transports.size(); i++) {
            int anotherWeight = transportData.getCargoWeightInTransport(transports.get(i));
            int currentWeight = transportData.getCargoWeightInTransport(truck);
            if (anotherWeight < currentWeight) {
                truck = transportData.getData().get(i);
            }
        }
        return truck;
    }

}
