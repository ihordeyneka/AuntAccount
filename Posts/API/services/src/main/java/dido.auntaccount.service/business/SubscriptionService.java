package dido.auntaccount.service.business;

import dido.auntaccount.dto.SubscriptionDTO;

import java.util.List;

public interface SubscriptionService {

    void sendNotifications(Long userId, String text);

    void saveSubscription(SubscriptionDTO subscriptionDTO);
}
