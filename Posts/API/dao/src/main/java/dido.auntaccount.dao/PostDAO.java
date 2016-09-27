package dido.auntaccount.dao;

import dido.auntaccount.entities.Post;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by orysiadeyneka on 24.09.16.
 */
public interface PostDAO {

    Post find(Long id);

    Post save(Post post) throws Exception;

}
