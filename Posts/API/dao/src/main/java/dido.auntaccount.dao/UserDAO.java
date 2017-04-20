package dido.auntaccount.dao;

import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.User;

import java.util.List;

public interface UserDAO {

    User find(Long userId);

    User findByEmail(String userName);

    User save(User user) throws Exception;

    void delete(User user) throws Exception;

    List<Post> getPostsByUserId(Long userId);

    List<Review> getReviewsByUserId(Long userId);

    List<PostDTO> getUserPostsWithOfferCount(Long userId);

    void updateUser(User user);

    void updatePicture(Long id, byte[] image);

}
