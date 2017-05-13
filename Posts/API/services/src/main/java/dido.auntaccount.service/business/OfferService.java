package dido.auntaccount.service.business;

import dido.auntaccount.dto.MessageDTO;
import dido.auntaccount.dto.OfferDTO;
import dido.auntaccount.dto.SellerDTO;

import java.util.List;

public interface OfferService {

    OfferDTO getOffer(Long offerId);

    OfferDTO saveOffer(OfferDTO offer);

    SellerDTO getOfferSeller(Long offerId);

    List<MessageDTO> getOfferMessages(Long offerId);

}
