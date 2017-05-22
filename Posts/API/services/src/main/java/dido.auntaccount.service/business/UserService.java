package dido.auntaccount.service.business;

import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.ReviewDTO;
import dido.auntaccount.dto.SellerDTO;
import dido.auntaccount.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getUser(Long userId);

    UserDTO findByEmail(String userName);

    UserDTO saveUser(UserDTO user);

    List<PostDTO> getUserPosts(Long userId);

    List<SellerDTO> getUserSellers(Long userId);

    List<ReviewDTO> getUserReviews(Long userId);

    void updateUserProfile(UserDTO user);

    void updatePicture(Long userId, byte[] picture);

    void updateUser(UserDTO user);

}
