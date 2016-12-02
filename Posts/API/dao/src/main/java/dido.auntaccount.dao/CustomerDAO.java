package dido.auntaccount.dao;


import dido.auntaccount.entities.Customer;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;

import java.util.List;

public interface CustomerDAO {

    Customer find(Long customerId);

    Customer save(Customer customer) throws Exception;

    void delete(Customer customer) throws Exception;

    List<Post> getPostsByCustomerId(Long customerId);

    List<Review> getReviewsByCustomerId(Long customerId);

}
