package ru.liga.loader.util;

import org.springframework.stereotype.Component;
import ru.liga.loader.model.entity.Cargo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CargoCounter {

    private final int INCREMENT_VALUE = 1;

    /**
     * Подсчитывает количество грузов каждого типа.
     *
     * @param cargos список грузов для подсчета
     * @return мапа с количеством грузов для каждого типа
     */

    public Map<String, Integer> count(List<Cargo> cargos) {
        Map<String, Integer> cargosCount = new HashMap<>();
        for (Cargo cargo : cargos) {
            incrementCount(cargosCount, cargo.getName());
        }
        return cargosCount;
    }

    private void incrementCount(Map<String, Integer> countMap, String name) {
        countMap.put(
                name,
                countMap.getOrDefault(name, 0) + INCREMENT_VALUE
        );
    }
}
