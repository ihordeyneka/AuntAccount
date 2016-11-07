package dido.auntaccount.service.business;

import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Post;

import java.util.List;

public interface PostService {

    Post getPost(Long postId);

    Post savePost(Post post);

    List<Offer> getPostOffers(Long postId);

}
