package dido.auntaccount.service.business;

import dido.auntaccount.dto.*;

import java.io.InputStream;
import java.util.List;

public interface OfferService {

    OfferDTO getOffer(Long offerId);

    OfferDTO saveOffer(OfferMessageDTO offer, Long loggedInUser);

    OfferDTO saveOffer(OfferDTO offer);

    SellerDTO getOfferSeller(Long offerId);

    List<MessageDTO> getOfferMessages(Long offerId);

    OfferDTO saveOfferPicture(InputStream uploadedInputStream, Long postId, Long sellerId, Long loggedInUserId);

    PostDTO getOfferPost(Long offerId);

}
