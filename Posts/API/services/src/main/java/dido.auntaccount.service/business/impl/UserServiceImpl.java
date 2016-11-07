package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.User;
import dido.auntaccount.service.business.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Inject
    private UserDAO userDAO;

    @Override
    public User getUser(Long userId) {
        return userDAO.find(userId);
    }

    @Override
    public User saveUser(User user) {
        User savedUser = null;
        try {
            savedUser = userDAO.save(user);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save supplier", e);
        }
        return savedUser;
    }

    @Override
    public List<Post> getUserPosts(Long userId) {
        return userDAO.getPostsByUserId(userId);
    }

    @Override
    public List<Review> getUserReviews(Long userId) {
        return userDAO.getReviewsByUserId(userId);
    }
}
