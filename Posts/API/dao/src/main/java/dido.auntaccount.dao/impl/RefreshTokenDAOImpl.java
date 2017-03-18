package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.RefreshTokenDAO;
import dido.auntaccount.entities.RefreshToken;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class RefreshTokenDAOImpl extends GeneralDAO<RefreshToken> implements RefreshTokenDAO {

    @Inject
    public RefreshTokenDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public RefreshToken find(String token) {
        return findEntity(token, RefreshToken.class);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) throws Exception {
        return persistEntity(refreshToken);
    }

    @Override
    public void delete(RefreshToken refreshToken) throws Exception {
        throw new UnsupportedOperationException("not implemented yet");
    }

}
