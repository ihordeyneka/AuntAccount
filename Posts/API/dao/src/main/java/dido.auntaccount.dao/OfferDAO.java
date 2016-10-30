package dido.auntaccount.dao;

import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.Offer;

import java.util.List;

public interface OfferDAO {

    public Offer find(Long offerId);

    public Offer save(Offer offer) throws Exception;

    public void delete(Offer offer) throws Exception;

    public List<Message> getMessagesByOfferId(Long offerId);

}
