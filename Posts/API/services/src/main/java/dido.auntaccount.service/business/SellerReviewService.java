package dido.auntaccount.service.business;

import dido.auntaccount.dto.SellerReviewDTO;

import java.util.List;

public interface SellerReviewService {

    SellerReviewDTO getReview(Long reviewId);

    SellerReviewDTO saveReview(SellerReviewDTO review);

    List<SellerReviewDTO> getSellerReviews(Long sellerId);

}
