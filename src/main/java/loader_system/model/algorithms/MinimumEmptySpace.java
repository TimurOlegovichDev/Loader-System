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
                tryFindEmptySpaceAndLoad(cargo, transportData);
            } catch (NoPlaceException e) {
                Transport truck = new TruckFactory().createTransport();
                truck.loadCargo(cargo, truck.getBody().length-1,0);
                transportData.add(truck);
            }
        }
    }

    private static void tryFindEmptySpaceAndLoad(Cargo cargo, TransportData transports) throws NoPlaceException {
        for(Transport transport : transports.getData()){
            for(int i = transport.getBody().length - 1; i >= 0; i--){
                for(int j = 0; j < transport.getBody()[i].length; j++){
                    if(canInsertInTruck(i, j, cargo, transport)) {
                        transport.loadCargo(cargo, i, j);
                        return;
                    }
                }
            }
        }
        throw new NoPlaceException();
    }

    private static boolean canInsertInTruck(int indexBodyHeight, int indexBodyWidth, Cargo cargo, Transport transport) {
        char[][] cpBody = transport.getBody();
        int i = indexBodyHeight;
        for(char[] boxLine : cargo.getForm()){
            int j = indexBodyWidth;
            for(Character character : boxLine){
                try {
                    if(cpBody[i][j]==' ') {
                        cpBody[i][j] = character;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
                j++;
            }
            i--;
        }
        return true;
    }
}
