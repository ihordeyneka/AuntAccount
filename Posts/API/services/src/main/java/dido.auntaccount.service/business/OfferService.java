package dido.auntaccount.service.business;

import dido.auntaccount.dto.MessageDTO;
import dido.auntaccount.dto.OfferMessageDTO;
import dido.auntaccount.dto.OfferDTO;
import dido.auntaccount.dto.SellerDTO;

import java.util.List;

public interface OfferService {

    OfferDTO getOffer(Long offerId);

    OfferDTO saveOffer(OfferMessageDTO offer, Long loggedInUser);

    SellerDTO getOfferSeller(Long offerId);

    List<MessageDTO> getOfferMessages(Long offerId);

}
