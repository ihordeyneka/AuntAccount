package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.TokenDAO;
import dido.auntaccount.entities.Token;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class TokenDAOImpl extends GeneralDAO<Token> implements TokenDAO {

    @Inject
    public TokenDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Token find(String token) {
        TypedQuery<Token> query = entityManager.createQuery("SELECT t FROM Token t WHERE t.token = :token", Token.class);
        return query.setParameter("token", token).getSingleResult();
    }

    public Token save(Token token) throws Exception {
        return persistEntity(token);
    }

    public void delete(Token token) throws Exception {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void deleteExpiredTokens() {
        throw new UnsupportedOperationException("not implemented yet");
    }

}
