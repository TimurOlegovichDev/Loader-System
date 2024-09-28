package ru.liga.loader.repository;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.model.entity.Transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TransportDataRepository {

    private final Map<Transport, List<Cargo>> transportMap;

    @Autowired
    public TransportDataRepository(Map<Transport, List<Cargo>> transportMap) {
        this.transportMap = transportMap;
    }

    /**
     * Возвращает копию списка транспортных средств, хранящихся в менеджере.
     *
     * @return копия списка транспортных средств
     */

    public List<Transport> getData() {
        return new ArrayList<>(transportMap.keySet());
    }

    /**
     * Возвращает список грузов, соответствующих указанному транспортному средству.
     *
     * @param transport транспортное средство, для которого нужно получить список грузов
     * @return список грузов
     */

    public List<Cargo> getCargos(Transport transport) {
        return transportMap.get(transport);
    }

    /**
     * Добавляет транспортное средство в основное хранилище.
     *
     * @param transport транспортное средство, которое будет добавлено
     */

    public void add(Transport transport) {
        transportMap.put(transport, new ArrayList<>());
    }

    /**
     * Добавляет список транспортных средств в в основное хранилище.
     *
     * @param transports список транспортных средств, который будет добавлен
     */

    public void add(@NonNull List<Transport> transports) {
        transports.forEach(this::add);
    }

    /**
     * Добавляет мапу транспортных средств и грузов в основное хранилище.
     *
     * @param map карта транспортных средств и грузов, которая будет добавлена
     */

    public void add(Map<Transport, List<Cargo>> map) {
        transportMap.putAll(map);
    }

    /**
     * Добавляет груз в транспортное средство.
     *
     * @param transport транспортное средство, в которое будет добавлен груз
     * @param cargo     груз, который будет добавлен
     */

    public void addCargoInTransport(Transport transport, Cargo cargo) {
        transportMap.get(transport).add(cargo);
    }

    /**
     * Возвращает общий вес грузов в транспортном средстве.
     *
     * @param transport транспортное средство, для которого нужно получить общий вес грузов
     * @return общий вес грузов
     */

    public int getCargoWeightInTransport(Transport transport) {
        int weight = 0;
        List<Cargo> cargos = getCargos(transport);
        if (cargos == null) {
            return weight;
        }
        for (Cargo cargoInTransport : cargos) {
            weight += cargoInTransport.getArea();
        }
        return weight;
    }

    /**
     * Удаляет транспортное средство из основного хранилища.
     *
     * @param transport транспортное средство, которое будет удалено
     */

    public void remove(Transport transport) {
        transportMap.remove(transport);
    }

    /**
     * Возвращает строковое представление карты транспортных средств и грузов.
     *
     * @return строковое представление карты транспортных средств и грузов
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Transport transport : transportMap.keySet())
            sb.append(transport.toString()).append(System.lineSeparator());
        return sb.toString();
    }
}
