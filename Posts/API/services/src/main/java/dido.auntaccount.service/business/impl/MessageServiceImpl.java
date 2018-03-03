package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.MessageDAO;
import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.dto.*;
import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.User;
import dido.auntaccount.service.business.MessageService;
import dido.auntaccount.service.business.OfferService;
import dido.auntaccount.service.business.SubscriptionService;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LogManager.getLogger(MessageServiceImpl.class);

    @Inject
    private MessageDAO messageDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private SubscriptionService subscriptionService;

    @Inject
    private OfferService offerService;

    @Override
    public MessageDTO getMessage(Long id) {
        Message message = messageDAO.find(id);
        return new MessageDTO(message);
    }

    @Override
    public MessageDTO saveMessage(MessageDTO message) {
        return saveMessage(message.buildEntity());
    }

    @Override
    public MessageDTO saveMessage(InputStream uploadedInputStream, Long offerId, Long loggedInUserId) {
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(uploadedInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User sender = userDAO.find(loggedInUserId);
        Message message = new Message()
                .setCreationDate(DateTime.now().toDate())
                .setOfferId(offerId)
                .setSender(sender)
                .setPhoto(bytes);
        return saveMessage(message);
    }

    public MessageDTO saveMessage(Message message) {
        Message savedMessage = null;
        try {
            savedMessage = messageDAO.save(message);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save message", e);
        }
        subscriptionService.sendNotifications(getMessageReceiver(message), message.getDescription());
        return new MessageDTO(savedMessage);
    }

    private Long getMessageReceiver(Message message) {
        final User sender = message.getSender();
        final OfferDTO offer = offerService.getOffer(message.getOfferId());
        final Long sellerId = offer.getSeller().getId();
        if (sellerId.equals(sender.getId())) {
            return sellerId;
        }
        final PostDTO offerPost = offerService.getOfferPost(message.getOfferId());
        return offerPost.getUserId();
    }
}
