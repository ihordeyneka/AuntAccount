package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.CustomerDAO;
import dido.auntaccount.entities.Customer;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.service.business.CustomerService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

    @Inject
    private CustomerDAO customerDAO;

    @Override
    public Customer getCustomer(Long customerId) {
        return customerDAO.find(customerId);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = null;
        try {
            savedCustomer = customerDAO.save(customer);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save customer", e);
        }
        return savedCustomer;
    }

    @Override
    public List<Post> getCustomerPosts(Long customerId) {
        return customerDAO.getPostsByCustomerId(customerId);
    }

    @Override
    public List<Review> getCustomerReviews(Long customerId) {
        return customerDAO.getReviewsByCustomerId(customerId);
    }
}
