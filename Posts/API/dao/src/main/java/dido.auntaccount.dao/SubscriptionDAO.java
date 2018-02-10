package dido.auntaccount.dao;

import dido.auntaccount.entities.Subscription;

import java.util.List;

public interface SubscriptionDAO {

    List<Subscription> getUserSubscriptions(Long userId);

    void saveSubscription(Subscription subscription) throws Exception;
}
