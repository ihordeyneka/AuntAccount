package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.PostDAO;
import dido.auntaccount.dto.OfferDTO;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Post;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PostDAOImpl extends GeneralDAO<Post> implements PostDAO {

    @Inject
    public PostDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Post find(Long id) {
        return findEntity(id, Post.class);
    }

    public Post save(Post post) throws Exception {
        return persistEntity(post);
    }

    public void delete(Post post) throws Exception {
        deleteEntity(post.getId(), Post.class);
    }

    public List<Offer> getOffersByPostId(Long postId) {
        TypedQuery<Offer> query = entityManager.createQuery("SELECT o FROM Offer o WHERE o.postId = :postId", Offer.class);
        return query.setParameter("postId", postId).getResultList();
    }

    //TODO: get back offer-supplier FK
    public List<OfferDTO> getPostOffersWithReplies(Long postId) {
        Post post = find(postId);
        List<Object[]> resultList = entityManager.createNativeQuery("SELECT *, count(m.id) as replyCount FROM OFFER o LEFT JOIN MESSAGE m ON o.id = m.offerId WHERE o.postId = ?1 AND m.senderId != ?2 AND m.isRead = 0 GROUP BY m.offerId",
                "OfferReplyCountMapping")
                .setParameter(1, postId)
                .setParameter(2, post.getUserId()).getResultList();
        List<OfferDTO> offers = new ArrayList<>();
        resultList.forEach((record) -> {
            Offer offer = (Offer) record[0];
            Long replyCount = (Long) record[1];
            offers.add(new OfferDTO(offer, replyCount.intValue()));
        });
        return offers;
    }

    public void updatePhoto(Long postId, byte[] photo) {
        TypedQuery<Post> query = entityManager.createQuery("UPDATE Post SET photo = :photo WHERE postId = :postId", Post.class);
        query.setParameter("postId", postId)
                .setParameter("photo", photo)
                .executeUpdate();
    }
}
