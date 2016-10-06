package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.PostDAO;
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

    public List<Post> findByUser(Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> cq = cb.createQuery(Post.class);
        Root<Post> post = cq.from(Post.class);
        cq.select(post);

        ParameterExpression<Long> params = cb.parameter(Long.class);
        cq.where(cb.equal(post.get("userId"), params));

        TypedQuery<Post> q = entityManager.createQuery(cq);
        q.setParameter(params, userId);
        return q.getResultList();
    }

    public Post save(Post post) throws Exception {
        return persistEntity(post);
    }
}
