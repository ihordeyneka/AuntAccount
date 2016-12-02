package dido.auntaccount.service.business;

import dido.auntaccount.entities.User;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;

import java.util.List;

public interface UserService {

    User getUser(Long userId);

    User findByUserName(String userName);

}
