package dido.auntaccount.service.business;

import dido.auntaccount.entities.Review;

public interface ReviewService {

    Review getReview(Long reviewId);

    Review saveReview(Review review);

}
