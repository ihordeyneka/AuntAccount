package dido.auntaccount.dao;

import dido.auntaccount.entities.Post;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public interface PostDAO {

    public Post find(Long id);

    public Post save(Post post) throws Exception;

    public void delete(Post post);

}
