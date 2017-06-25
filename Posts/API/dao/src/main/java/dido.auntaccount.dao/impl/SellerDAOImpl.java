package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.SellerDAO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Seller;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class SellerDAOImpl extends GeneralDAO<Seller> implements SellerDAO {

    @Inject
    public SellerDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Seller find(Long sellerId) {
        return findEntity(sellerId, Seller.class);
    }

    public Seller save(Seller seller) throws Exception {
        return persistEntity(seller);
    }

    public void addSellerPost(Long sellerId, Post post) {
        Seller seller = find(sellerId);
        seller.getSellerPosts().add(post);
        try {
            updateEntity(seller);
        } catch (Exception e) {
            throw new IllegalStateException("Couldn't save post for seller");
        }
    }

    @Override
    public void deleteSeller(Long sellerId) throws Exception {
        deleteEntity(sellerId, Seller.class);
    }

    @Override
    public Seller findByName(String name) {
        TypedQuery<Seller> query = entityManager.createQuery("SELECT s FROM Seller s WHERE s.name = :name", Seller.class)
                .setParameter("name", name);
        return getSingleResultOrNull(query);
    }

    @Override
    public boolean hasSellers(Long userId) {
        final Query query = entityManager.createQuery("SELECT count(s) FROM Seller s WHERE s.userId = :userId")
                .setParameter("userId", userId);
        final Long count = (Long) query.getSingleResult();
        return count != 0;
    }

}
