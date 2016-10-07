package dido.auntaccount.dao;


import dido.auntaccount.entities.User;

public interface UserDAO {

    public User findUser(Long userId);

}
