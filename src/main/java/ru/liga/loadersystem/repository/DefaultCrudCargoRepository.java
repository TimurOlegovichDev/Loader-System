package ru.liga.loadersystem.repository;

import org.springframework.stereotype.Repository;
import ru.liga.loadersystem.model.entity.Cargo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Repository
public class DefaultCrudCargoRepository implements CargoCrudRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void updateFormForName(String name, String newForm) {
        List<Cargo> cargosToUpdate = entityManager.createQuery(
                "SELECT c FROM Cargo c WHERE c.name = :name",
                Cargo.class
        ).setParameter("name", name).getResultList();
        for (Cargo cargo : cargosToUpdate) {
            cargo.setForm(newForm);
        }
        entityManager.flush();
    }

    @Override
    @Transactional
    public void updateNameForName(String name, String newName) {
        List<Cargo> cargosToUpdate = entityManager.createQuery(
                "SELECT c FROM Cargo c WHERE c.name = :name",
                Cargo.class
        ).setParameter("name", name).getResultList();
        for (Cargo cargo : cargosToUpdate) {
            cargo.setName(newName);
        }
        entityManager.flush();
    }

    @Transactional
    public void updateTypeForName(String name, String newType) {
        List<Cargo> cargosToUpdate = entityManager.createQuery(
                "SELECT c FROM Cargo c WHERE c.name = :name",
                Cargo.class
        ).setParameter("name", name).getResultList();
        for (Cargo cargo : cargosToUpdate) {
            String newForm = cargo.getForm().replaceAll("[^;]", newType);
            cargo.setForm(newForm);
        }
        entityManager.flush();
    }

    @Override
    @Transactional
    public Cargo findByName(String name) {
        try {
            List<Cargo> results = entityManager.createQuery(
                    "SELECT c FROM Cargo c WHERE c.name = :name",
                    Cargo.class
            ).setParameter("name", name).getResultList();
            if (!results.isEmpty()) {
                return results.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public List<Cargo> findAllByTransportId(UUID uuidTransport) {
        return entityManager.createQuery(
                "SELECT c FROM Cargo c WHERE c.transportId = :transportId",
                Cargo.class
        ).setParameter("transportId", uuidTransport).getResultList();
    }

    @Override
    @Transactional
    public <S extends Cargo> S save(S entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    @Transactional
    public <S extends Cargo> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return entities;
    }

    @Override
    @Transactional
    public Optional<Cargo> findById(UUID uuid) {
        return Optional.ofNullable(entityManager.find(Cargo.class, uuid));
    }

    @Override
    @Transactional
    public boolean existsById(UUID uuid) {
        return findById(uuid).isPresent();
    }

    @Override
    @Transactional
    public Iterable<Cargo> findAll() {
        return entityManager.createQuery(
                "SELECT c FROM Cargo c",
                Cargo.class).getResultList();
    }

    @Override
    @Transactional
    public Iterable<Cargo> findAllLoaded() {
        return entityManager.createQuery(
                "SELECT c FROM Cargo c WHERE c.transportId IS NOT NULL",
                Cargo.class).getResultList();
    }

    @Override
    @Transactional
    public List<Cargo> findAllUnique() {
        List<Cargo> results = entityManager.createQuery(
                "SELECT c FROM Cargo c",
                Cargo.class
        ).getResultList();
        Map<String, Cargo> uniqueCargos = new HashMap<>();
        for (Cargo cargo : results) {
            uniqueCargos.putIfAbsent(cargo.getName(), cargo);
        }
        return uniqueCargos.values().stream().toList();
    }

    @Override
    @Transactional
    public Iterable<Cargo> findAllById(Iterable<UUID> uuids) {
        return entityManager.createQuery("SELECT c FROM Cargo c WHERE c.id IN :ids", Cargo.class)
                .setParameter("ids", uuids)
                .getResultList();
    }

    @Override
    @Transactional
    public long count() {
        return entityManager.createQuery("SELECT COUNT(t) FROM Transport t", Long.class)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void deleteById(UUID uuid) {
        entityManager.remove(findById(uuid));
    }

    @Override
    @Transactional
    public void delete(Cargo entity) {
        entityManager.remove(entity);
    }

    @Override
    @Transactional
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        entityManager.createQuery("DELETE FROM Cargo c WHERE c.id IN :ids");
    }

    @Override
    @Transactional
    public void deleteAllByTransportId(UUID uuid) {
        entityManager.createQuery("DELETE FROM Cargo c WHERE c.transportId = :id")
                .setParameter("id", uuid)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends Cargo> entities) {
        entities.forEach(this::delete);
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.remove(Cargo.class);
    }
}