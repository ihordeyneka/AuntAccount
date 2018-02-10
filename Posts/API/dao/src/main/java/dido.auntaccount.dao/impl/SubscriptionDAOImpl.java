package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.SubscriptionDAO;
import dido.auntaccount.entities.Subscription;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class SubscriptionDAOImpl extends GeneralDAO<Subscription> implements SubscriptionDAO {

    @Inject
    public SubscriptionDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Subscription> getUserSubscriptions(Long userId) {
        TypedQuery<Subscription> query = entityManager.createQuery("SELECT s FROM Subscription s WHERE s.userId = :userId", Subscription.class);
        return query.setParameter("userId", userId).getResultList();
    }

    @Override
    public void saveSubscription(Subscription subscription) throws Exception {
        persistEntity(subscription);
    }
}
