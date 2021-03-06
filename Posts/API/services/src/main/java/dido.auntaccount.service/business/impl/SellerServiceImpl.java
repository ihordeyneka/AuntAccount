package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.CountryDAO;
import dido.auntaccount.dao.SellerDAO;
import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.SellerDTO;
import dido.auntaccount.dto.TagDTO;
import dido.auntaccount.entities.Country;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Seller;
import dido.auntaccount.search.SearchSellerService;
import dido.auntaccount.service.business.SellerService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SellerServiceImpl implements SellerService {

    private static final Logger logger = LogManager.getLogger(SellerServiceImpl.class);

    @Inject
    private SellerDAO sellerDAO;

    @Inject
    private CountryDAO countryDAO;

    @Inject
    SearchSellerService searchSellerService;

    @Override
    public SellerDTO getSeller(Long sellerId) {
        Seller seller = sellerDAO.find(sellerId);
        return new SellerDTO(seller);
    }

    @Override
    public SellerDTO getSeller(String name) {
        Seller seller = sellerDAO.findByName(name);
        return seller != null ? new SellerDTO(seller) : null;
    }

    @Override
    public SellerDTO saveSeller(SellerDTO seller) {
        Seller savedSeller = null;
        try {
            final String country = seller.getLocation().getCountry().getCountry();
            final Country existingCountry = countryDAO.find(country);
            seller.setCreationDate(DateTime.now().toDate());
            final Seller sellerEntity = seller.buildEntity();
            if (existingCountry != null) {
                sellerEntity.getLocation().setCountry(existingCountry);
            }
            savedSeller = sellerDAO.save(sellerEntity);
            searchSellerService.saveSeller(new SellerDTO(savedSeller));
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save seller", e);
        }
        return new SellerDTO(savedSeller);
    }

    @Override
    public List<PostDTO> getSellerPosts(Long sellerId) {
        Seller seller = sellerDAO.find(sellerId);
        if (seller != null) {
            List<Post> sellerPosts = seller.getSellerPosts();
            return sellerPosts.stream().map(PostDTO::new).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Seller> savePostForSellers(PostDTO post) {
        List<TagDTO> postTags = post.getTags();
        List<String> tags = postTags.stream().map(TagDTO::getTag).collect(Collectors.toList());

        List<Long> sellerIds = searchSellerService.getSellerIdsByTagsAndLocation(tags, post.getLocation());
        List<Seller> sellers = sellerIds.stream().map(s -> sellerDAO.find(s)).filter(Objects::nonNull).collect(Collectors.toList());
        sellers.stream().forEach(s -> sellerDAO.addSellerPost(s, post.buildEntity()));
        return sellers;
    }

    @Override
    public void updatePicture(SellerDTO seller, byte[] picture) throws Exception {
        final Seller entity = seller.buildEntity();
        entity.setPhoto(picture);
        sellerDAO.save(entity);
    }

    @Override
    public void deleteSeller(Long sellerId) throws Exception {
        sellerDAO.deleteSeller(sellerId);
    }

    @Override
    public SellerDTO updateSeller(SellerDTO seller) {
        try {
            final Seller updatedSeller = sellerDAO.updateSeller(seller.buildEntity());
            final SellerDTO sellerDTO = new SellerDTO(updatedSeller);
            searchSellerService.updateSeller(sellerDTO);
            return sellerDTO;
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't update seller", e);
        }
        return null;
    }
}
