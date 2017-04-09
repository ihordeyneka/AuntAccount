package dido.auntaccount.service.business;

import dido.auntaccount.dto.OfferDTO;
import dido.auntaccount.dto.PostDTO;

import java.io.InputStream;
import java.util.List;

public interface PostService {

    PostDTO getPost(Long postId);

    PostDTO savePost(PostDTO post);

    List<OfferDTO> getPostOffers(Long postId);

    PostDTO updatePhoto(InputStream stream, Long postId);

}
