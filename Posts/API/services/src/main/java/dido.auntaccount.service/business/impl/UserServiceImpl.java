package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.SellerDAO;
import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.dto.*;
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
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Inject
    private UserDAO userDAO;

    @Inject
    private SearchSellerService searchSellerService;

    @Inject
    private SellerDAO sellerDAO;

    @Override
    public UserDTO getUser(Long userId) {
        return new UserDTO(userDAO.find(userId));
    }

    @Override
    public UserProfileDTO getUserProfile(Long userId) {
        final UserProfileDTO userProfileDTO = new UserProfileDTO(userDAO.find(userId));
        final boolean hasSellers = sellerDAO.hasSellers(userId);
        userProfileDTO.setHasSellers(hasSellers);
        return userProfileDTO;
    }

    @Override
    public UserProfileDTO findByEmail(String email) {
        User user = userDAO.findByEmail(email);
        return user != null ? new UserProfileDTO(user) : null;
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
    public NotificationListDTO getUserNotifications(Long userId, int offset, int limit) {
        final List<NotificationDTO> notifications = userDAO.getUserNotifications(userId, offset, limit);
        final long count = userDAO.getUserNotificationCount(userId);
        return new NotificationListDTO(count, notifications);
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
