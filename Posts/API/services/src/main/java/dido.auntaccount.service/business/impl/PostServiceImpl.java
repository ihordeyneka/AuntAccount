package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.PostDAO;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Post;
import dido.auntaccount.service.business.PostService;
import dido.auntaccount.service.business.SupplierService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class PostServiceImpl implements PostService {

    private static final Logger logger = LogManager.getLogger(PostServiceImpl.class);

    @Inject
    private PostDAO postDAO;

    @Inject
    private SupplierService supplierService;

    @Override
    public Post getPost(Long postId) {
        return postDAO.find(postId);
    }

    @Override
    public Post savePost(Post post) {
        Post savedPost = null;
        try {
            savedPost = postDAO.save(post);
            supplierService.savePostForSuppliers(savedPost);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save post", e);
        }
        return savedPost;
    }

    @Override
    public List<Offer> getPostOffers(Long postId) {
        return postDAO.getOffersByPostId(postId);
    }
}
