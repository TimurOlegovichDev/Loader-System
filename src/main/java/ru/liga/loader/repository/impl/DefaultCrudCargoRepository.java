package ru.liga.loader.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.repository.CargoCrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DefaultCrudCargoRepository implements CargoCrudRepository {

    private final Map<String, Cargo> cargoData;

    @Autowired
    public DefaultCrudCargoRepository(Map<String, Cargo> cargoData) {
        this.cargoData = cargoData;
    }

    /**
     * Возвращает список всех грузов.
     *
     * @return список грузов
     */

    @Override
    public List<Cargo> getAll() {
        return new ArrayList<>(cargoData.values());
    }

    /**
     * Возвращает список имен всех грузов.
     *
     * @return список имен грузов
     */

    @Override
    public List<String> getKeys() {
        return cargoData.keySet().stream().toList();
    }


    /**
     * Возвращает груз по его имени.
     *
     * @param key имя груза
     * @return груз, если он найден, иначе null
     */

    @Override
    public Cargo getBy(String key) {
        return cargoData.get(key);
    }

    /**
     * Добавляет несколько грузов в репозиторий.
     *
     * @param entities мапа, содержащая грузы с их именами
     */

    @Override
    public void addAll(Map<String, Cargo> entities) {
        cargoData.putAll(entities);
    }

    /**
     * Обновляет груз с указанным именем.
     *
     * @param key   имя груза
     * @param value новый груз
     * @return обновленный груз
     */

    @Override
    public Cargo update(String key, Cargo value) {
        return cargoData.put(key, value);
    }

    /**
     * Удаляет груз с указанным именем.
     *
     * @param id имя груза
     * @return удаленный груз
     */

    @Override
    public Cargo delete(String id) {
        return cargoData.remove(id);
    }

    /**
     * Добавляет груз в репозиторий.
     *
     * @param cargo груз
     * @return добавленный груз
     */

    @Override
    public Cargo put(Cargo cargo) {
        return cargoData.put(cargo.getName(), cargo);
    }

    /**
     * Возвращает строковое представление репозитория.
     *
     * @return строковое представление репозитория
     */

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Cargo cargo : cargoData.values()) {
            builder.append(cargo);
        }
        return builder.toString();
    }
}