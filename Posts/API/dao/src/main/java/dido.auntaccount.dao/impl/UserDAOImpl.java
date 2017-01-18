package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAOImpl extends GeneralDAO<User> implements UserDAO {

    @Inject
    public UserDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public User find(Long userId) {
        return findEntity(userId, User.class);
    }

    public User findByUserName(String userName) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE (TYPE(u) = Supplier or TYPE(u) = Customer) and u.name = :name", User.class);
        return query.setParameter("name", userName).getSingleResult();
    }

}