package dido.auntaccount.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class GeneralDAO<T> implements AutoCloseable {

    protected final EntityManager entityManager;

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
            entity = entityManager.merge(entity);
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

    protected <T> void updateEntity(T entity) throws Exception {
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

    public static <T> T getSingleResultOrNull(TypedQuery<T> query) {
        query.setMaxResults(1);
        List<T> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

}
