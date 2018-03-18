package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dto.NotificationDTO;
import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.SellerDTO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.SellerReview;
import dido.auntaccount.entities.Seller;
import dido.auntaccount.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAOImpl extends GeneralDAO<User> implements UserDAO {

    @Inject
    public UserDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public User find(Long userId) {
        return findEntity(userId, User.class);
    }

    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email);
        return getSingleResultOrNull(query);
    }

    public User save(User user) throws Exception {
        return persistEntity(user);
    }

    public void delete(User user) throws Exception {
        deleteEntity(user.getId(), User.class);
    }

    public List<Post> getPostsByUserId(Long userId) {
        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post p WHERE p.userId = :userId", Post.class);
        return query.setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Seller> getSellersByUserId(Long userId) {
        TypedQuery<Seller> query = entityManager.createQuery("SELECT s FROM Seller s WHERE s.userId = :userId", Seller.class);
        return query.setParameter("userId", userId).getResultList();
    }

    public List<PostDTO> getUserPostsWithOfferCount(Long userId) {
        List<Object[]> resultList = entityManager.createNativeQuery("SELECT p.*, count(o.id) as offerCount" +
                        " FROM Post p LEFT JOIN Offer o ON p.id = o.postId WHERE p.userId=?1 GROUP BY p.id",
                "PostOfferCountMapping")
                .setParameter(1, userId).getResultList();
        List<PostDTO> posts = new ArrayList<>();
        resultList.forEach((record) -> {
            Post post = (Post) record[0];
            Long offerCount = (Long) record[1];
            posts.add(new PostDTO(post, offerCount.intValue()));
        });
        return posts;
    }

    @Override
    public void updateUserProfile(User user) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            final Query query = entityManager.createQuery("UPDATE User SET firstName=:firstName, lastName=:lastName WHERE id=:userId");
            query.setParameter("firstName", user.getFirstName())
                    .setParameter("lastName", user.getLastName())
                    .setParameter("userId", user.getId())
                    .executeUpdate();
            et.commit();

        } catch (Exception e) {
            rollback(et);
            throw e;
        }
    }

    @Override
    public void updateUser(User user) throws Exception {
        updateEntity(user);
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId, int offset, int limit) {
        List<Object[]> resultList = entityManager.createNativeQuery("SELECT s.id, s.name, p.id, p.creationDate, p.description  " +
                " FROM Seller s JOIN SellerPost sp ON s.id = sp.sellerId AND s.userId=?1 JOIN Post p ON sp.postId = p.id LIMIT ?2 OFFSET ?3")
                .setParameter(1, userId)
                .setParameter(2, limit)
                .setParameter(3, offset).getResultList();
        List<NotificationDTO> notifications = new ArrayList<>();
        resultList.forEach((record) -> {
            NotificationDTO notification = new NotificationDTO();
            SellerDTO seller = new SellerDTO();
            seller.setId(Long.valueOf((Integer) record[0]));
            seller.setName((String) record[1]);
            notification.setSeller(seller);
            notification.setPostId((Long) record[2]);
            notification.setCreationDate((Date) record[3]);
            notification.setDescription((String) record[4]);
            notifications.add(notification);
        });
        return notifications;
    }

    @Override
    public long getUserNotificationCount(Long userId) {
        Query query = entityManager.createNativeQuery("SELECT count(*)  " +
                " FROM Seller s JOIN SellerPost sp ON s.id = sp.sellerId AND s.userId=?1 JOIN Post p ON sp.postId = p.id")
                .setParameter(1, userId);
        return (long) query.getSingleResult();
    }

    @Override
    public void updatePicture(Long id, byte[] image) throws Exception {
        final User user = find(id);
        if (user == null) {
            return;
        }
        user.setPhoto(image);
        updateEntity(user);
    }

}