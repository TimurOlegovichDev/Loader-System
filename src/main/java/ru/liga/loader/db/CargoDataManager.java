package ru.liga.loader.db;

import ru.liga.loader.model.entity.Cargo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CargoDataManager {

    private final Map<String, List<Cargo>> cargoData;

    public CargoDataManager(Map<String, List<Cargo>> cargoData) {
        this.cargoData = cargoData;
    }

    /**
     * Возвращает копию списка грузов, хранящихся в менеджере.
     *
     * @return копия списка грузов
     */

    public Map<String, List<Cargo>> getData() {
        return new HashMap<>(cargoData);
    }

    /**
     * Добавляет груз в список грузов, хранящихся в менеджере.
     *
     * @param cargo груз, который будет добавлен
     */


    public void add(Cargo cargo) {
        if (!cargoData.containsKey(cargo.getName())) {
            cargoData.put(cargo.getName(), new ArrayList<>());
        }
        cargoData.get(cargo.getName()).add(cargo);
    }

    /**
     * Добавляет грузы в список грузов, хранящихся в менеджере.
     *
     * @param cargos список грузов, который будет добавлен
     */

    public void add(Map<String, List<Cargo>> cargos) {
        cargoData.putAll(cargos);
    }

    /**
     * Возвращает строковое представление списка грузов, хранящихся в менеджере.
     *
     * @return строковое представление списка грузов
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Cargo>> entry : cargoData.entrySet()) {
            sb.append(entry.getKey())
                    .append(":")
                    .append(System.lineSeparator());
            for (Cargo cargo : entry.getValue()) {
                sb.append(cargo.toString())
                        .append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
