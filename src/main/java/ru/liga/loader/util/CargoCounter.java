package ru.liga.loader.util;

import ru.liga.loader.model.entites.Cargo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CargoCounter {

    private final int INCREMENT_VALUE = 1;

    public Map<Character, Integer> countCargos(List<Cargo> cargos) {
        Map<Character, Integer> cargosCount = new HashMap<>();
        for (Cargo cargo : cargos) {
            char symbol = cargo.getType();
            incrementCount(cargosCount, symbol);
        }
        return cargosCount;
    }

    private void incrementCount(Map<Character, Integer> countMap, char symbol) {
        countMap.put(
                symbol,
                countMap.getOrDefault(symbol, 0) + INCREMENT_VALUE
        );
    }
}
