package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.MessageDAO;
import dido.auntaccount.dao.OfferDAO;
import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.dto.*;
import dido.auntaccount.entities.*;
import dido.auntaccount.service.business.OfferService;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class OfferServiceImpl implements OfferService {

    private static final Logger logger = LogManager.getLogger(OfferServiceImpl.class);

    @Inject
    private OfferDAO offerDAO;

    @Inject
    private MessageDAO messageDAO;

    @Inject
    private UserDAO userDAO;

    @Override
    public OfferDTO getOffer(Long offerId) {
        Offer offer = offerDAO.find(offerId);
        return new OfferDTO(offer);
    }

    @Override
    public OfferDTO saveOffer(OfferMessageDTO offer, Long loggedInUser) {
        final OfferDTO savedOffer = saveOffer(offer.buildEntity());
        Message message = new Message().setDescription(offer.getDescription());
        saveMessage(message, loggedInUser, savedOffer.getId());
        return savedOffer;
    }

    public OfferDTO saveOffer(OfferDTO offer) {
        Offer savedOffer = null;
        try {
            savedOffer = offerDAO.save(offer.buildEntity());
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save offer", e);
        }
        return new OfferDTO(savedOffer);
    }

    @Override
    public SellerDTO getOfferSeller(Long offerId) {
        Offer offer = offerDAO.find(offerId);
        return new SellerDTO(offer.getSeller());
    }

    @Override
    public List<MessageDTO> getOfferMessages(Long offerId) {
        List<Message> messages = offerDAO.getMessagesByOfferId(offerId);
        return messages.stream().map(MessageDTO::new).collect(Collectors.toList());
    }

    @Override
    public OfferDTO saveOfferPicture(InputStream uploadedInputStream, Long postId, Long sellerId, Long loggedInUserId) {
        final Seller seller = new Seller().setId(sellerId);
        Offer offer = new Offer()
                .setPostId(postId)
                .setSeller(seller);
        OfferDTO savedOffer = saveOffer(offer);

        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(uploadedInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = new Message().setPhoto(bytes);
        saveMessage(message, loggedInUserId, savedOffer.getId());
        return savedOffer;
    }

    @Override
    public PostDTO getOfferPost(Long offerId) {
        final Post post = offerDAO.getOfferPost(offerId);
        return post != null ? new PostDTO(post) : null;
    }

    private OfferDTO saveOffer(Offer offer) {
        Offer savedOffer = null;
        try {
            savedOffer = offerDAO.save(offer);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save offer", e);
        }
        return new OfferDTO(savedOffer);
    }

    private MessageDTO saveMessage(Message message, Long userId, Long offerId) {
        final User user = new User().setId(userId);
        message.setCreationDate(DateTime.now().toDate())
                .setOfferId(offerId)
                .setSender(user);
        Message savedMessage = null;
        try {
            savedMessage = messageDAO.save(message);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save message", e);
        }
        return new MessageDTO(savedMessage);
    }

}
