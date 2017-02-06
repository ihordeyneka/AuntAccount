package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.ReviewDAO;
import dido.auntaccount.entities.Review;
import dido.auntaccount.service.business.ReviewService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

    @Inject
    private ReviewDAO reviewDAO;

    @Override
    public Review getReview(Long reviewId) {
        return reviewDAO.find(reviewId);
    }

    @Override
    public Review saveReview(Review review) {
        Review savedReview = null;
        try {
            savedReview = reviewDAO.save(review);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save review", e);
        }
        return savedReview;
    }

}
