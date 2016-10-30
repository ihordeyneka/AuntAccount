package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAOImpl extends GeneralDAO<User> implements UserDAO {

    @Inject
    public UserDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public User find(Long id) {
        return findEntity(id, User.class);
    }

    public User save(User User) throws Exception {
        return persistEntity(User);
    }

    public void delete(User user) throws Exception {
        deleteEntity(user.getId(), User.class);
    }

    public List<Post> getPostsByUserId(Long userId) {
        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post p WHERE o.userId = :userId", Post.class);
        return query.setParameter("userId", userId).getResultList();
    }

    public List<Review> getReviewsByUserId(Long userId) {
        TypedQuery<Review> query = entityManager.createQuery("SELECT r FROM Review r WHERE r.objectId = :objectId", Review.class);
        return query.setParameter("objectId", userId).getResultList();
    }

}
