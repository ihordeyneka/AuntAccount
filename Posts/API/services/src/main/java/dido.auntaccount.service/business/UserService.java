package dido.auntaccount.service.business;

import dido.auntaccount.dto.*;

import java.util.List;

public interface UserService {

    UserDTO getUser(Long userId);

    UserProfileDTO getUserProfile(Long userId);

    UserProfileDTO findByEmail(String userName);

    UserDTO saveUser(UserProfileDTO user);

    List<PostDTO> getUserPosts(Long userId);

    List<SellerDTO> getUserSellers(Long userId);

    List<ReviewDTO> getUserReviews(Long userId);

    NotificationListDTO getUserNotifications(Long userId, int offset, int limit);

    void updateUserProfile(UserDTO user);

    void updatePicture(Long userId, byte[] picture);

    void updateUser(UserProfileDTO user);

}
