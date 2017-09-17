package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.SellerDAO;
import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.dao.VerificationTokenDAO;
import dido.auntaccount.dto.*;
import dido.auntaccount.entities.Seller;
import dido.auntaccount.entities.User;
import dido.auntaccount.entities.VerificationToken;
import dido.auntaccount.search.SearchSellerService;
import dido.auntaccount.service.business.EmailService;
import dido.auntaccount.service.business.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    public static final int EXPIRY_TIME_IN_HOURS = 24;

    @Inject
    private UserDAO userDAO;

    @Inject
    private SearchSellerService searchSellerService;

    @Inject
    private SellerDAO sellerDAO;

    @Inject
    private VerificationTokenDAO verificationTokenDAO;

    @Inject
    private EmailService emailService;

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
        if (user != null) {
            final UserProfileDTO userProfileDTO = new UserProfileDTO(user);
            final boolean hasSellers = sellerDAO.hasSellers(user.getId());
            userProfileDTO.setHasSellers(hasSellers);
            return userProfileDTO;
        }
        return null;
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
        final UserProfileDTO userDTO = new UserProfileDTO(savedUser);
        final String token = generateVerificationToken();
        createVerificationToken(userDTO, token);
        emailService.sendCompleteRegistration(userDTO.getEmail(), token);
        return userDTO.buildUserDTO();
    }

    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
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
        updateUser(user.buildEntity());
    }

    private void updateUser(User user) {
        try {
            userDAO.updateUser(user);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't update user", e);
        }
    }

    private void createVerificationToken(UserProfileDTO user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token)
                .setUser(user.buildEntity())
                .setExpirationDate(calculateExpiryDate(EXPIRY_TIME_IN_HOURS));
        try {
            verificationTokenDAO.save(verificationToken);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't create verification token", e);
        }

    }

    @Override
    public VerificationTokenDTO getVerificationToken(String token) {
        final VerificationToken verificationToken = verificationTokenDAO.find(token);
        return verificationToken != null ? new VerificationTokenDTO(verificationToken) : null;
    }

    @Override
    public void activateUser(UserDTO userDTO) {
        final User entity = userDTO.buildEntity();
        entity.setEnabled(true);
        updateUser(entity);
    }

    private Date calculateExpiryDate(int expiryTimeInHours) {
        return new Date(DateTime.now().plusHours(expiryTimeInHours).getMillis());
    }

}
