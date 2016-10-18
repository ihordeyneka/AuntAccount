package dido.auntaccount.dao;


import dido.auntaccount.entities.User;

public interface UserDAO {

    public User find(Long userId);

    public User save(User user) throws Exception;

    public void delete(User user) throws Exception;

}
