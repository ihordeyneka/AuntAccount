package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.ReviewDTO;
import dido.auntaccount.dto.UserDTO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.User;
import dido.auntaccount.service.business.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Inject
    private UserDAO userDAO;

    @Override
    public UserDTO getUser(Long userId) {
        return new UserDTO(userDAO.find(userId));
    }

    @Override
    public UserDTO findByUserName(String userName) {
        User user = userDAO.findByUserName(userName);
        return new UserDTO(user);
    }

    @Override
    public UserDTO saveUser(UserDTO user) {
        User savedUser = null;
        try {
            savedUser = userDAO.save(user.buildEntity());
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save user", e);
        }
        return new UserDTO(savedUser);
    }

    @Override
    public List<PostDTO> getUserPosts(Long userId) {
        List<Post> posts = userDAO.getPostsByUserId(userId);
        return posts.stream().map(PostDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getUserReviews(Long userId) {
        List<Review> reviews = userDAO.getReviewsByUserId(userId);
        return reviews.stream().map(ReviewDTO::new).collect(Collectors.toList());
    }


}
