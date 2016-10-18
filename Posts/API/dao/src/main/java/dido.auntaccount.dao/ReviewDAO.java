package dido.auntaccount.dao;

import dido.auntaccount.entities.Review;

public interface ReviewDAO {

    public Review find(Long reviewId);

    public Review save(Review review) throws Exception;

    public void delete(Review review) throws Exception;

}
