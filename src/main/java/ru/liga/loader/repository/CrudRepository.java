package ru.liga.loader.repository;

import java.util.List;
import java.util.Map;

public interface CrudRepository<K, V> {
    List<V> getAll();

    List<K> getKeys();

    V getBy(K key);

    void addAll(Map<K, V> entities);

    V update(K key, V value);

    V delete(K key);
}