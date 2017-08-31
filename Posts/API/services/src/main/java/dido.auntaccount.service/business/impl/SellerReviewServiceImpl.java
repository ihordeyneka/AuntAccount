package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.SellerDAO;
import dido.auntaccount.dao.SellerReviewDAO;
import dido.auntaccount.dto.SellerReviewDTO;
import dido.auntaccount.entities.Seller;
import dido.auntaccount.entities.SellerReview;
import dido.auntaccount.service.business.SellerReviewService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class SellerReviewServiceImpl implements SellerReviewService {

    private static final Logger logger = LogManager.getLogger(SellerReviewServiceImpl.class);

    @Inject
    private SellerReviewDAO reviewDAO;

    @Inject
    private SellerDAO sellerDAO;

    @Override
    public SellerReviewDTO getReview(Long reviewId) {
        SellerReview review = reviewDAO.find(reviewId);
        return new SellerReviewDTO(review);
    }

    @Override
    public SellerReviewDTO getUserReview(Long sellerId, Long userId) {
        final SellerReview userReview = reviewDAO.getUserReview(sellerId, userId);
        return userReview != null ? new SellerReviewDTO(userReview) : null;
    }

    @Override
    public SellerReviewDTO saveReview(SellerReviewDTO review) {
        SellerReview savedReview = null;
        try {
            savedReview = reviewDAO.save(review.buildEntity());
            updateSellerRate(review.getSellerId());
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save review", e);
        }
        return new SellerReviewDTO(savedReview);
    }

    @Override
    public List<SellerReviewDTO> getSellerReviews(Long sellerId) {
        final List<SellerReview> reviews = reviewDAO.getSellerReviews(sellerId);
        return reviews.stream().map(SellerReviewDTO::new).collect(Collectors.toList());
    }

    @Override
    public SellerReviewDTO updateReview(SellerReviewDTO review) {
        SellerReview savedReview = null;
        try {
            savedReview = reviewDAO.update(review.buildEntity());
            updateSellerRate(review.getSellerId());
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't update review", e);
        }
        return new SellerReviewDTO(savedReview);
    }

    private void updateSellerRate(Long sellerId) {
        final Seller seller = sellerDAO.find(sellerId);
        final double rate = reviewDAO.calculateSellerRate(sellerId);
        seller.setRate(rate);
        sellerDAO.updateSeller(seller);
    }

}
