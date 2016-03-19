package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.DBConnection;
import dido.auntaccount.dao.business.UserDao;
import dido.auntaccount.entities.User;

import java.util.List;

/**
 * Created by orysiadeyneka on 19.03.16.
 */
public class UserDaoImpl implements UserDao {

    public User saveUser(User user) {
        DBConnection.openConnection();
        user.saveIt();
        DBConnection.closeConnection();
        return user;
    }

    public User getUserById(Long id) {
        DBConnection.openConnection();
        DBConnection.closeConnection();
        return null;
    }

    public List<User> getAllUsers() {
        DBConnection.openConnection();
        DBConnection.closeConnection();
        return null;
    }

    public void deleteUserById(Long id) {
        DBConnection.openConnection();
        DBConnection.closeConnection();
    }

}
