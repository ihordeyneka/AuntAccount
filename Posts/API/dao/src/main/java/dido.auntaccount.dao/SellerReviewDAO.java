package dido.auntaccount.dao;

import dido.auntaccount.entities.SellerReview;

import java.util.List;

public interface SellerReviewDAO {

    SellerReview find(Long reviewId);

    SellerReview save(SellerReview review) throws Exception;

    void delete(SellerReview review) throws Exception;

    List<SellerReview> getSellerReviews(Long sellerId);

}
