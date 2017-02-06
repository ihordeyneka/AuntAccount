package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.OfferDAO;
import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Supplier;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class OfferDAOImpl extends GeneralDAO<Offer> implements OfferDAO {

    @Inject
    public OfferDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Offer find(Long id) {
        return findEntity(id, Offer.class);
    }

    public Offer save(Offer offer) throws Exception {
        Long supplierId = offer.getSupplier().getId();
        Supplier supplier = entityManager.getReference(Supplier.class, supplierId);
        offer.setSupplier(supplier);
        return persistEntity(offer);
    }

    public void delete(Offer offer) throws Exception {
        deleteEntity(offer.getId(), Offer.class);
    }

    public List<Message> getMessagesByOfferId(Long offerId) {
        TypedQuery<Message> query = entityManager.createQuery("SELECT m FROM Message m WHERE m.offerId = :offerId", Message.class);
        return query.setParameter("offerId", offerId).getResultList();
    }

}
