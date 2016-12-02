package dido.auntaccount.dao;

import dido.auntaccount.entities.User;

public interface UserDAO {

    User find(Long userId);

    User findByUserName(String userName);
}
