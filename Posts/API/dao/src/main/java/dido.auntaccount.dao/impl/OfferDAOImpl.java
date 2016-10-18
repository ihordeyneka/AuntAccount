package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.OfferDAO;
import dido.auntaccount.entities.Offer;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class OfferDAOImpl extends GeneralDAO<Offer> implements OfferDAO {

    @Inject
    public OfferDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Offer find(Long id) {
        return findEntity(id, Offer.class);
    }

    public Offer save(Offer Offer) throws Exception {
        return persistEntity(Offer);
    }

    public void delete(Offer offer) throws Exception {
        deleteEntity(offer.getId(), Offer.class);
    }

}
