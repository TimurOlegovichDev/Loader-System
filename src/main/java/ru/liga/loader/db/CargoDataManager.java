package ru.liga.loader.db;


import ru.liga.loader.model.entity.Cargo;

import java.util.ArrayList;
import java.util.List;

public class CargoDataManager {

    private final List<Cargo> cargoData;

    public CargoDataManager(List<Cargo> cargoData) {
        this.cargoData = cargoData;
    }

    /**
     * Возвращает копию списка грузов, хранящихся в менеджере.
     *
     * @return копия списка грузов
     */

    public List<Cargo> getData() {
        return new ArrayList<>(cargoData);
    }

    /**
     * Добавляет груз в список грузов, хранящихся в менеджере.
     *
     * @param cargo груз, который будет добавлен
     */


    public void add(Cargo cargo) {
        cargoData.add(cargo);
    }

    /**
     * Добавляет список грузов в список грузов, хранящихся в менеджере.
     *
     * @param cargos список грузов, который будет добавлен
     */

    public void add(List<Cargo> cargos) {
        cargoData.addAll(cargos);
    }

    /**
     * Возвращает строковое представление списка грузов, хранящихся в менеджере.
     *
     * @return строковое представление списка грузов
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cargo cargo : cargoData)
            sb.append(cargo.toString()).append("\n");
        return sb.toString();
    }
}
