package ru.liga.loader.repository;

import org.springframework.stereotype.Repository;
import ru.liga.loader.model.entity.Transport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DefaultCrudTransportRepository implements TransportCrudRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public <S extends Transport> S save(S entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    @Transactional
    public <S extends Transport> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(entityManager::merge);
        return entities;
    }

    @Override
    @Transactional
    public Optional<Transport> findById(UUID uuid) {
        return Optional.ofNullable(entityManager.find(Transport.class, uuid));
    }

    @Override
    @Transactional
    public boolean existsById(UUID uuid) {
        return findById(uuid).isPresent();
    }

    @Override
    @Transactional
    public Iterable<Transport> findAll() {
        return entityManager.createQuery("SELECT t FROM Transport t", Transport.class).getResultList();
    }

    @Override
    @Transactional
    public Iterable<Transport> findAllById(Iterable<UUID> uuids) {
        return entityManager.createQuery("SELECT t FROM Transport t WHERE t.id IN :ids", Transport.class)
                .setParameter("ids", uuids)
                .getResultList();
    }

    @Override
    @Transactional
    public long count() {
        return entityManager.createQuery("SELECT COUNT(t) FROM Transport t", Long.class).getSingleResult();
    }

    @Override
    @Transactional
    public void deleteById(UUID uuid) {
        entityManager.remove(entityManager.find(Transport.class, uuid));
    }

    @Override
    @Transactional
    public void delete(Transport entity) {
        entityManager.remove(entity);
    }

    @Override
    @Transactional
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        entityManager.createQuery("DELETE FROM Transport t WHERE t.id IN :ids");
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends Transport> entities) {
        for (Transport entity : entities) {
            delete(entity);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.remove(Transport.class);
    }
}