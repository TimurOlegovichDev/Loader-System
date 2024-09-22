package loader.utils;

import loader.model.entites.Cargo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CargoCounter {

    public Map<Character, Integer> countCargos(List<Cargo> cargos) {
        Map<Character, Integer> cargosCount = new HashMap<>();
        for (Cargo cargo : cargos) {
            char symbol = cargo.getType();
            if (cargosCount.containsKey(symbol)) {
                cargosCount.put(
                        symbol,
                        cargosCount.get(symbol) + 1 // Увеличиваем количество имеющихся коробок на одну
                );
            } else {
                cargosCount.put(
                        symbol,
                        1 // Указываем изначальное количество имеющихся коробок данного типа
                );
            }
        }
        return cargosCount;
    }
}
