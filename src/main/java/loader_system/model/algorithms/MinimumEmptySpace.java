package loader_system.model.algorithms;

import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.entites.cargos.Cargo;
import loader_system.model.entites.transports.Transport;
import loader_system.model.exceptions.NoPlaceException;
import loader_system.model.factories.transport.TruckFactory;

import java.util.List;

public class MinimumEmptySpace implements Algorithm{

    @Override
    public void execute(CargoData cargoData, TransportData transportData) {
        List<Cargo> cargos = cargoData.getData();
        cargos.sort((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        for(Cargo cargo : cargos){
            try {
                tryFindEmptySpace(cargo, transportData);
            } catch (NoPlaceException e) {
                Transport truck = new TruckFactory().createTransport();
                truck.loadCargo(cargo, 0,0);
                transportData.add(truck);
            }
        }
    }

    private static void tryFindEmptySpace(Cargo cargo, TransportData transports) throws NoPlaceException {
        for(Transport transport : transports.getData()){
            for(int i = 0; i < transport.getBody().length; i++){
                for(int j = 0; j < transport.getBody()[i].length; j++){
                    if(tryInsertInTruck(i, j, cargo, transport)) {
                        transport.loadCargo(cargo, i, j);
                        return;
                    }
                }
            }
        }
        throw new NoPlaceException();
    }

    private static boolean tryInsertInTruck(int indexBodyHeight, int indexBodyWidth, Cargo cargo, Transport transport) {
        char[][] copyList = transport.getBody();
        int i = 0;
        for(char[] boxLine : cargo.getForm()){
            int j = 0;
            for(Character character : boxLine){
                try {
                    if(copyList[i+indexBodyHeight][j+indexBodyWidth]==' ') {
                        copyList[i + indexBodyHeight][j + indexBodyWidth] = character;
                    } else {
                        return false;
                    }
                } catch (IndexOutOfBoundsException e) {
                    return false;
                }
                j++;
            }
            i++;
        }
        return true;
    }
}
