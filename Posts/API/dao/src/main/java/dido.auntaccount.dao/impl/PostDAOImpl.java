package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.PostDAO;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Post;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by orysiadeyneka on 24.09.16.
 */
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
}
