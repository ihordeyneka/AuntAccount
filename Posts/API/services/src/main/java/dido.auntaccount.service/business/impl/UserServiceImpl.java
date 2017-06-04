package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.dto.*;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.Seller;
import dido.auntaccount.entities.User;
import dido.auntaccount.search.SearchSellerService;
import dido.auntaccount.service.business.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Inject
    private UserDAO userDAO;

    @Inject
    private SearchSellerService searchSellerService;

    @Override
    public UserDTO getUser(Long userId) {
        return new UserDTO(userDAO.find(userId));
    }

    @Override
    public UserProfileDTO getUserProfile(Long userId) {
        return new UserProfileDTO(userDAO.find(userId));
    }

    @Override
    public UserProfileDTO findByEmail(String email) {
        User user = userDAO.findByEmail(email);
        return new UserProfileDTO(user);
    }

    @Override
    public UserDTO saveUser(UserProfileDTO user) {
        User savedUser = null;
        try {
            User entity = user.buildEntity();
            entity.setCreationDate(new Date(DateTime.now().getMillis()));
            savedUser = userDAO.save(entity);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save user", e);
        }
        return new UserDTO(savedUser);
    }

    @Override
    public List<PostDTO> getUserPosts(Long userId) {
        return userDAO.getUserPostsWithOfferCount(userId);
        /*List<Post> posts = userDAO.getPostsByUserId(userId);
        return posts.stream().map(PostDTO::new).collect(Collectors.toList());*/
    }

    @Override
    public List<SellerDTO> getUserSellers(Long userId) {
        final List<Seller> sellers = userDAO.getSellersByUserId(userId);
        return sellers.stream().map(SellerDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getUserReviews(Long userId) {
        List<Review> reviews = userDAO.getReviewsByUserId(userId);
        return reviews.stream().map(ReviewDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId, int offset, int limit) {
        return userDAO.getUserNotifications(userId, offset, limit);
    }

    public void updateUserProfile(UserDTO user) {
        userDAO.updateUserProfile(user.buildEntity());
    }

    public void updatePicture(Long userId, byte[] picture) {
        try {
            userDAO.updatePicture(userId, picture);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't update user's picture", e);
        }
    }

    @Override
    public void updateUser(UserProfileDTO user) {
        try {
            userDAO.updateUser(user.buildEntity());
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't update user", e);
        }
    }

}
