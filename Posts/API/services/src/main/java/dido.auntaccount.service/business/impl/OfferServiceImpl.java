package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.OfferDAO;
import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Supplier;
import dido.auntaccount.service.business.OfferService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class OfferServiceImpl implements OfferService {

    private static final Logger logger = LogManager.getLogger(OfferServiceImpl.class);

    @Inject
    private OfferDAO offerDAO;

    @Override
    public Offer getOffer(Long offerId) {
        return offerDAO.find(offerId);
    }

    @Override
    public Offer saveOffer(Offer offer) {
        Offer savedOffer = null;
        try {
            savedOffer = offerDAO.save(offer);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save offer", e);
        }
        return savedOffer;
    }

    @Override
    public Supplier getOfferSupplier(Long offerId) {
        Offer offer = offerDAO.find(offerId);
        return offer.getSupplier();
    }

    @Override
    public List<Message> getOfferMessages(Long offerId) {
        return offerDAO.getMessagesByOfferId(offerId);
    }
}
