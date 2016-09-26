package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.PostDAO;
import dido.auntaccount.entities.Post;

import javax.inject.Inject;
import javax.persistence.EntityManager;

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
}
