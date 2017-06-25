package dido.auntaccount.dao;

import dido.auntaccount.dto.NotificationDTO;
import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Seller;
import dido.auntaccount.entities.User;

import java.util.List;

public interface UserDAO {

    User find(Long userId);

    User findByEmail(String userName);

    User save(User user) throws Exception;

    void delete(User user) throws Exception;

    List<Post> getPostsByUserId(Long userId);

    List<Seller> getSellersByUserId(Long userId);

    List<PostDTO> getUserPostsWithOfferCount(Long userId);

    void updateUserProfile(User user);

    void updatePicture(Long id, byte[] image) throws Exception;

    void updateUser(User user) throws Exception;

    List<NotificationDTO> getUserNotifications(Long userId, int offset, int limit);

    long getUserNotificationCount(Long userId);

}
