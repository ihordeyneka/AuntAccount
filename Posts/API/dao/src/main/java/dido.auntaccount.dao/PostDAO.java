package dido.auntaccount.dao;

import dido.auntaccount.dto.OfferDTO;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Post;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public interface PostDAO {

    Post find(Long id);

    Post save(Post post) throws Exception;

    void delete(Post post) throws Exception;

    List<Offer> getOffersByPostId(Long postId);

    void updatePhoto(Long postId, byte[] photo);

    public List<OfferDTO> getPostOffersWithReplies(Long postId);

}
