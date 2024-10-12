package ru.liga.loadersystem.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.liga.loadersystem.model.entity.Cargo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface CargoCrudRepository extends CrudRepository<Cargo, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE Cargo c SET c.form = :newForm WHERE c.name = :name")
    void updateFormForName(@Param("name") String name, @Param("newForm") String newForm);

    @Modifying
    @Transactional
    @Query("UPDATE Cargo c SET c.name = :newName WHERE c.name = :name")
    void updateNameForName(@Param("name") String name, @Param("newName") String newName);

    Cargo findByName(String name);

    List<Cargo> findAllByTransportId(UUID uuidTransport);

    @Transactional
    @Query("SELECT c FROM Cargo c WHERE c.transportId IS NOT NULL")
    Iterable<Cargo> findAllLoaded();

    @Transactional
    @Query("SELECT c FROM Cargo c")
    List<Cargo> findAllUnique();

    @Modifying
    @Transactional
    @Query("DELETE FROM Cargo c WHERE c.transportId = :id")
    void deleteAllByTransportId(@Param("id") UUID uuid);
}
