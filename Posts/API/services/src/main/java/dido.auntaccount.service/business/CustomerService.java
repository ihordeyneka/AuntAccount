package dido.auntaccount.service.business;

import dido.auntaccount.entities.Customer;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;

import java.util.List;

public interface CustomerService {

    Customer getCustomer(Long customerId);

    Customer saveCustomer(Customer customer);

    List<Post> getCustomerPosts(Long customerId);

    List<Review> getCustomerReviews(Long customerId);

}
