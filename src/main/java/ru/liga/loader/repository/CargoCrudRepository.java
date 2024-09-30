package ru.liga.loader.repository;

import ru.liga.loader.model.entity.Cargo;

public interface CargoCrudRepository extends CrudRepository<String, Cargo> {

    Cargo add(Cargo cargo);
}
