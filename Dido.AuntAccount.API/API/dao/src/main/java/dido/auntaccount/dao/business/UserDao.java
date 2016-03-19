package dido.auntaccount.dao.business;

import dido.auntaccount.entities.User;

import java.util.List;

/**
 * Created by orysiadeyneka on 19.03.16.
 */
public interface UserDao {

    public User saveUser(User user);

    public User getUserById(Long id);

    public List<User> getAllUsers();

    public void deleteUserById(Long id);

}
