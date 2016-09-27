package dido.auntaccount.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by orysiadeyneka on 25.09.16.
 */
public abstract class GeneralDAO<T> implements AutoCloseable {

    private final EntityManager entityManager;

    public GeneralDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected <T> T findEntity(Object id, Class<T> entityClass) {
        return entityManager.find(entityClass, id);
    }

    protected <T> T persistEntity(T entity) throws Exception {
        EntityTransaction et = entityManager.getTransaction();

        try {
            et.begin();
            entityManager.persist(entity);
            et.commit();

            return entity;
        } catch (Exception e) {
            rollback(et);
            throw e;
        }
    }

    protected <T> void deleteEntity(Object id, Class<T> entityClass) throws Exception {
        T entity = findEntity(id, entityClass);

        if (entity == null)
            return;

        EntityTransaction et = entityManager.getTransaction();

        try {
            et.begin();
            entityManager.remove(entity);
            et.commit();

        } catch (Exception e) {
            rollback(et);
            throw e;
        }
    }

    protected <T> void updateEntity(Object id, Class<T> entityClass) throws Exception {
        T entity = findEntity(id, entityClass);

        if (entity == null)
            return;

        EntityTransaction et = entityManager.getTransaction();

        try {
            et.begin();
            entityManager.merge(entity);
            et.commit();

        } catch (Exception e) {
            rollback(et);
            throw e;
        }
    }

    protected void rollback(EntityTransaction et) {
        if (et.isActive()) {
            et.rollback();
        }
    }

    public void close() throws Exception {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

}
