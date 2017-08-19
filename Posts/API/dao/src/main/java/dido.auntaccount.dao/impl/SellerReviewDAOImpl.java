package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.SellerReviewDAO;
import dido.auntaccount.entities.SellerReview;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class SellerReviewDAOImpl extends GeneralDAO<SellerReview> implements SellerReviewDAO {

    @Inject
    public SellerReviewDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public SellerReview find(Long id) {
        return findEntity(id, SellerReview.class);
    }

    public SellerReview save(SellerReview review) throws Exception {
        return persistEntity(review);
    }

    public void delete(SellerReview review) throws Exception {
        deleteEntity(review.getId(), SellerReview.class);
    }

    @Override
    public List<SellerReview> getSellerReviews(Long sellerId) {
        TypedQuery<SellerReview> query = entityManager.createQuery("SELECT r FROM SellerReview r WHERE r.sellerId = :sellerId", SellerReview.class)
                .setParameter("sellerId", sellerId);
        return query.getResultList();
    }

    @Override
    public SellerReview getUserReview(Long sellerId, Long userId) {
        TypedQuery<SellerReview> query = entityManager.createQuery("SELECT r FROM SellerReview r WHERE r.sellerId = :sellerId and r.userId = :userId", SellerReview.class)
                .setParameter("sellerId", sellerId)
                .setParameter("userId", userId);
        return getSingleResultOrNull(query);
    }

    @Override
    public SellerReview update(SellerReview sellerReview) throws Exception {
        updateEntity(sellerReview);
        return sellerReview;
    }

}
