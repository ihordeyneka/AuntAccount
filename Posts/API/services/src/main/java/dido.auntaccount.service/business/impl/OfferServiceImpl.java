package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.MessageDAO;
import dido.auntaccount.dao.OfferDAO;
import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.dto.*;
import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.User;
import dido.auntaccount.service.business.OfferService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import javax.inject.Inject;
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
        Offer savedOffer = null;
        try {
            savedOffer = offerDAO.save(offer.buildEntity());
            final User user = userDAO.find(loggedInUser);
            final Message message = new Message()
                    .setDescription(offer.getDescription())
                    .setCreationDate(DateTime.now().toDate())
                    .setOfferId(savedOffer.getId())
                    .setSender(user);
            messageDAO.save(message);
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

}
