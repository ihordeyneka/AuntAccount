package dido.auntaccount.service.business;

import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Supplier;

import java.util.List;

public interface OfferService {

    Offer getOffer(Long offerId);

    Offer saveOffer(Offer offer);

    Supplier getOfferSupplier(Long offerId);

    List<Message> getOfferMessages(Long offerId);

}
