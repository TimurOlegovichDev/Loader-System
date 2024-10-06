package ru.liga.loader.repository;

import org.springframework.stereotype.Repository;
import ru.liga.loader.model.entity.Cargo;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DefaultCrudCargoRepository implements CargoCrudRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public Cargo findByName(String name) {
        try {
            return entityManager.createQuery(
                    "SELECT DISTINCT c FROM Cargo c WHERE c.name = :name",
                    Cargo.class
            ).setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
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
        return entityManager.createQuery("select c from Cargo c", Cargo.class).getResultList();
    }

    @Override
    @Transactional
    public Iterable<Cargo> findAllUnique() {
        return entityManager.createQuery("select distinct c from Cargo c", Cargo.class).getResultList();
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
    public void deleteAll(Iterable<? extends Cargo> entities) {
        entities.forEach(this::delete);
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.remove(Cargo.class);
    }
}