package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        return query.setParameter("email", email).getSingleResult();
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

    public List<PostDTO> getUserPostsWithOfferCount(Long userId) {
        List<Object[]> resultList = entityManager.createNativeQuery("SELECT *, count(o.id) as offerCount FROM POST p LEFT JOIN OFFER o ON p.id = o.postId WHERE p.userId=?1 GROUP BY o.postId",
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


    public List<Review> getReviewsByUserId(Long userId) {
        TypedQuery<Review> query = entityManager.createQuery("SELECT r FROM Review r WHERE r.objectId = :objectId", Review.class);
        return query.setParameter("objectId", userId).getResultList();
    }

}