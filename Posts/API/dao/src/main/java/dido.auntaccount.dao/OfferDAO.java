package dido.auntaccount.dao;

import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Post;

import java.util.List;

public interface OfferDAO {

    Offer find(Long offerId);

    Offer save(Offer offer) throws Exception;

    void delete(Offer offer) throws Exception;

    List<Message> getMessagesByOfferId(Long offerId);

    Post getOfferPost(Long offerId);
}
