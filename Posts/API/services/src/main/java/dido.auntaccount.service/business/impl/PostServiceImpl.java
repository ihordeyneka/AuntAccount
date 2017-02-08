package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.PostDAO;
import dido.auntaccount.dto.OfferDTO;
import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Post;
import dido.auntaccount.service.business.PostService;
import dido.auntaccount.service.business.SupplierService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class PostServiceImpl implements PostService {

    private static final Logger logger = LogManager.getLogger(PostServiceImpl.class);

    @Inject
    private PostDAO postDAO;

    @Inject
    private SupplierService supplierService;

    @Override
    public PostDTO getPost(Long postId) {
        Post post = postDAO.find(postId);
        return new PostDTO(post);
    }

    @Override
    public PostDTO savePost(PostDTO post) {
        PostDTO savedPost = null;
        try {
            savedPost = new PostDTO(postDAO.save(post.buildEntity()));
            supplierService.savePostForSuppliers(savedPost);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save post", e);
        }
        return savedPost;
    }

    @Override
    public List<OfferDTO> getPostOffers(Long postId) {
        List<Offer> offers = postDAO.getOffersByPostId(postId);
        return offers.stream().map(OfferDTO::new).collect(Collectors.toList());
    }

}
