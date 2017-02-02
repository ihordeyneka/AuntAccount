package dido.auntaccount.dao;

import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.User;

import java.util.List;

public interface UserDAO {

    User find(Long userId);

    User findByUserName(String userName);

    User save(User user) throws Exception;

    void delete(User user) throws Exception;

    List<Post> getPostsByUserId(Long userId);

    List<Review> getReviewsByUserId(Long userId);

}
