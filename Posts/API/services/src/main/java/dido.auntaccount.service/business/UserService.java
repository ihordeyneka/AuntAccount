package dido.auntaccount.service.business;

import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.ReviewDTO;
import dido.auntaccount.dto.UserDTO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.User;

import java.util.List;

public interface UserService {

    UserDTO getUser(Long userId);

    UserDTO findByUserName(String userName);

    UserDTO saveUser(UserDTO user);

    List<PostDTO> getUserPosts(Long userId);

    List<ReviewDTO> getUserReviews(Long userId);


}
