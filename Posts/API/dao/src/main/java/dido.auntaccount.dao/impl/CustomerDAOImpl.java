package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.CustomerDAO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.Customer;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerDAOImpl extends GeneralDAO<Customer> implements CustomerDAO {

    @Inject
    public CustomerDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Customer find(Long id) {
        return findEntity(id, Customer.class);
    }

    public Customer save(Customer customer) throws Exception {
        return persistEntity(customer);
    }

    public void delete(Customer customer) throws Exception {
        deleteEntity(customer.getId(), Customer.class);
    }

    public List<Post> getPostsByCustomerId(Long customerId) {
        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post p WHERE o.userId = :userId", Post.class);
        return query.setParameter("userId", customerId).getResultList();
    }

    public List<Review> getReviewsByCustomerId(Long customerId) {
        TypedQuery<Review> query = entityManager.createQuery("SELECT r FROM Review r WHERE r.objectId = :objectId", Review.class);
        return query.setParameter("objectId", customerId).getResultList();
    }

}
