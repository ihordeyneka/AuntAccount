package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.OfferDAO;
import dido.auntaccount.dto.MessageDTO;
import dido.auntaccount.dto.OfferDTO;
import dido.auntaccount.dto.SupplierDTO;
import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Supplier;
import dido.auntaccount.service.business.OfferService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class OfferServiceImpl implements OfferService {

    private static final Logger logger = LogManager.getLogger(OfferServiceImpl.class);

    @Inject
    private OfferDAO offerDAO;

    @Override
    public OfferDTO getOffer(Long offerId) {
        Offer offer = offerDAO.find(offerId);
        return new OfferDTO(offer);
    }

    @Override
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
    public SupplierDTO getOfferSupplier(Long offerId) {
        Offer offer = offerDAO.find(offerId);
        return new SupplierDTO(offer.getSupplier());
    }

    @Override
    public List<MessageDTO> getOfferMessages(Long offerId) {
        List<Message> messages = offerDAO.getMessagesByOfferId(offerId);
        return messages.stream().map(MessageDTO::new).collect(Collectors.toList());
    }

}
