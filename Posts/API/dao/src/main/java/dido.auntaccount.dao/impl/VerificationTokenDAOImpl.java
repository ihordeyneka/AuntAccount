package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.VerificationTokenDAO;
import dido.auntaccount.entities.VerificationToken;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class VerificationTokenDAOImpl extends GeneralDAO<VerificationToken> implements VerificationTokenDAO {

    @Inject
    public VerificationTokenDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public VerificationToken find(String token) {
        TypedQuery<VerificationToken> query = entityManager.createQuery("SELECT t FROM VerificationToken t WHERE t.token = :token", VerificationToken.class);
        return query.setParameter("token", token).getSingleResult();
    }

    public VerificationToken save(VerificationToken token) throws Exception {
        return persistEntity(token);
    }

    public void delete(String token) throws Exception {
        deleteEntity(token, VerificationToken.class);
    }
}
