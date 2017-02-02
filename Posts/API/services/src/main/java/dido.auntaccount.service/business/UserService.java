package dido.auntaccount.service.business;

import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.User;

import java.util.List;

public interface UserService {

    User getUser(Long userId);

    User findByUserName(String userName);

    User saveUser(User user);

    List<Post> getUserPosts(Long userId);

    List<Review> getUserReviews(Long userId);


}
