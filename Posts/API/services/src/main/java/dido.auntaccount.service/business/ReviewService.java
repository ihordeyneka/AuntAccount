package dido.auntaccount.service.business;

import dido.auntaccount.dto.ReviewDTO;
import dido.auntaccount.entities.Review;

public interface ReviewService {

    ReviewDTO getReview(Long reviewId);

    ReviewDTO saveReview(ReviewDTO review);

}
