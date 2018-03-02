package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.SubscriptionDAO;
import dido.auntaccount.dto.SubscriptionDTO;
import dido.auntaccount.entities.Subscription;
import dido.auntaccount.service.business.SubscriptionService;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;
import org.apache.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger logger = LogManager.getLogger(SubscriptionServiceImpl.class);
    public static final String PRIVATE_KEY = "B5lKvICGAwKeJXi1NrhVcS6kPhRXokyOYucj0wtk0Ns";
    public static final String PUBLIC_KEY = "BBHf1J50o1qRq_QPs_y-Lpg21Jo9hfMZlKjbO7sPJ-GxfjCS7qC9joosXVZrs6rM8sARigM7HgJGWG6beRmT4d0";

    private PushService pushService = new PushService();

    public SubscriptionServiceImpl() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            pushService.setPublicKey(Utils.loadPublicKey(PUBLIC_KEY));
            pushService.setPrivateKey(Utils.loadPrivateKey(PRIVATE_KEY));
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    @Inject
    private SubscriptionDAO subscriptionDAO;

    @Override
    public void sendNotifications(Long userId, String text) {
        final List<Subscription> userSubscriptions = subscriptionDAO.getUserSubscriptions(userId);
        final List<SubscriptionDTO> subscriptionDTOList = userSubscriptions.stream().map(SubscriptionDTO::new).collect(Collectors.toList());
        subscriptionDTOList.forEach(s -> sendNotification(s, text));
    }

    @Override
    public void saveSubscription(SubscriptionDTO subscriptionDTO) {
        try {
            subscriptionDAO.saveSubscription(subscriptionDTO.buildEntity());
        } catch (Exception e) {
            logger.error("Couldn't save subscription");
        }
    }

    private void sendNotification(SubscriptionDTO subscriptionDTO, String text) {

        Notification notification = null;
        try {
            notification = new Notification(subscriptionDTO.getEndpoint(), subscriptionDTO.getPublicKey(), subscriptionDTO.getAuth(), text);
            final Future<HttpResponse> httpResponseFuture = pushService.sendAsync(notification);
        } catch (Exception e) {
            logger.error("Couldn't sent notification", e);
        }
    }

}
