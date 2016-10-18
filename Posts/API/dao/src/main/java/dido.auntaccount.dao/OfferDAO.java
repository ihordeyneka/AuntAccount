package dido.auntaccount.dao;

import dido.auntaccount.entities.Offer;

public interface OfferDAO {

    public Offer find(Long offerId);

    public Offer save(Offer offer) throws Exception;

    public void delete(Offer offer) throws Exception;

}
