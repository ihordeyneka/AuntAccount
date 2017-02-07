package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.ReviewDAO;
import dido.auntaccount.dto.ReviewDTO;
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
    public ReviewDTO getReview(Long reviewId) {
        Review review = reviewDAO.find(reviewId);
        return new ReviewDTO(review);
    }

    @Override
    public ReviewDTO saveReview(ReviewDTO review) {
        Review savedReview = null;
        try {
            savedReview = reviewDAO.save(review.buildEntity());
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save review", e);
        }
        return new ReviewDTO(savedReview);
    }

}
