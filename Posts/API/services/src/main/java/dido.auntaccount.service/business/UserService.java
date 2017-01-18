package dido.auntaccount.service.business;

import dido.auntaccount.entities.User;

public interface UserService {

    User getUser(Long userId);

    User findByUserName(String userName);

}
