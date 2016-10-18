package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class UserDAOImpl extends GeneralDAO<User> implements UserDAO {

    @Inject
    public UserDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public User find(Long id) {
        return findEntity(id, User.class);
    }

    public User save(User User) throws Exception {
        return persistEntity(User);
    }

    public void delete(User user) throws Exception {
        deleteEntity(user.getId(), User.class);
    }

}
