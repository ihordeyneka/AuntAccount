package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.ReviewDAO;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class ReviewDAOImpl extends GeneralDAO<Review> implements ReviewDAO {

    @Inject
    public ReviewDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Review find(Long id) {
        return findEntity(id, Review.class);
    }

    public Review save(Review review) throws Exception {
        Long authorId = review.getAuthor().getId();
        User author = entityManager.getReference(User.class, authorId);
        review.setAuthor(author);
        return persistEntity(review);
    }

    public void delete(Review review) throws Exception {
        deleteEntity(review.getId(), Review.class);
    }

}
