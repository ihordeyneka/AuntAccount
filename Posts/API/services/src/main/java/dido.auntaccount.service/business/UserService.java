package dido.auntaccount.service.business;

import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.ReviewDTO;
import dido.auntaccount.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getUser(Long userId);

    UserDTO findByEmail(String userName);

    UserDTO saveUser(UserDTO user);

    List<PostDTO> getUserPosts(Long userId);

    List<ReviewDTO> getUserReviews(Long userId);


}
