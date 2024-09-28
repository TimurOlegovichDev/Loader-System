package ru.liga.loader.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.liga.loader.model.entity.Cargo;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CargoDataRepository {

    private final Map<String, Cargo> cargoData;

    @Autowired
    public CargoDataRepository(Map<String, Cargo> cargoData) {
        this.cargoData = cargoData;
    }

    /**
     * Возвращает копию списка грузов, хранящихся в менеджере.
     *
     * @return копия списка грузов
     */

    public Map<String, Cargo> getData() {
        return new HashMap<>(cargoData);
    }

    /**
     * Добавляет груз в список грузов, хранящихся в менеджере.
     *
     * @param cargo груз, который будет добавлен
     */


    public void add(Cargo cargo) {
        cargoData.put(cargo.getName(), cargo);
    }

    /**
     * Добавляет грузы в список грузов, хранящихся в менеджере.
     *
     * @param cargos список грузов, который будет добавлен
     */

    public void add(Map<String, Cargo> cargos) {
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
        for (Cargo cargo : cargoData.values()) {
            sb.append(cargo.toString())
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }
}
