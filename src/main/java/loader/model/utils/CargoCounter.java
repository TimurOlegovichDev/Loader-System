package loader.model.utils;

import loader.model.entites.cargos.Cargo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CargoCounter {

    public Map<Character, Integer> countCargos(List<Cargo> cargos){
        Map<Character, Integer> cargosCount = new HashMap<>();
        for (Cargo cargo : cargos){
            if(cargosCount.containsKey(cargo.getSymbol())){
                cargosCount.put(cargo.getSymbol(), cargosCount.get(cargo.getSymbol()) + 1);
            } else {
                cargosCount.put(cargo.getSymbol(), 1);
            }
        }
        return cargosCount;
    }
}
