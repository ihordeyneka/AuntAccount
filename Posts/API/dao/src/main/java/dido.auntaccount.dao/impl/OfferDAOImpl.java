package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.OfferDAO;
import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Seller;

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
        Long sellerId = offer.getSeller().getId();
        Seller seller = entityManager.getReference(Seller.class, sellerId);
        offer.setSeller(seller);
        return persistEntity(offer);
    }

    public void delete(Offer offer) throws Exception {
        deleteEntity(offer.getId(), Offer.class);
    }

    public List<Message> getMessagesByOfferId(Long offerId) {
        TypedQuery<Message> query = entityManager.createQuery("SELECT m FROM Message m WHERE m.offerId = :offerId", Message.class);
        return query.setParameter("offerId", offerId).getResultList();
    }

    @Override
    public Post getOfferPost(Long offerId) {
        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Offer o JOIN Post p ON o.postId= p.id WHERE o.id = :offerId", Post.class)
                .setParameter("offerId", offerId);
        return getSingleResultOrNull(query);
    }

}
