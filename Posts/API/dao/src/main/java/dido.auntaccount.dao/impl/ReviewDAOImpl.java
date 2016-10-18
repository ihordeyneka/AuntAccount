package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.ReviewDAO;
import dido.auntaccount.entities.Review;

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

    public Review save(Review Review) throws Exception {
        return persistEntity(Review);
    }

    public void delete(Review review) throws Exception {
        deleteEntity(review.getId(), Review.class);
    }

}
