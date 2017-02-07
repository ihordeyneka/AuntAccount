package dido.auntaccount.service.business;

import dido.auntaccount.dto.MessageDTO;
import dido.auntaccount.dto.OfferDTO;
import dido.auntaccount.dto.SupplierDTO;
import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Supplier;

import java.util.List;

public interface OfferService {

    OfferDTO getOffer(Long offerId);

    OfferDTO saveOffer(OfferDTO offer);

    SupplierDTO getOfferSupplier(Long offerId);

    List<MessageDTO> getOfferMessages(Long offerId);

}
