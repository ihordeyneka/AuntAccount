package dido.auntaccount.dao;


import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.User;

import java.util.List;

public interface UserDAO {

    public User find(Long userId);

    public User save(User user) throws Exception;

    public void delete(User user) throws Exception;

    public List<Post> getPostsByUserId(Long userId);

    public List<Review> getReviewsByUserId(Long userId);

}
