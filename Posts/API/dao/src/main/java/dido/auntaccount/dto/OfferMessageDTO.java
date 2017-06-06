package dido.auntaccount.dto;

import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Seller;

public class OfferMessageDTO implements DTO<Offer> {

    private Long id;
    private SellerDTO seller;
    private Long postId;
    private String description;

    public OfferMessageDTO() {
    }

    public OfferMessageDTO(Offer offer) {
        this.id = offer.getId();
        Seller entitySeller = offer.getSeller();
        this.seller = entitySeller != null ? new SellerDTO(entitySeller) : null;
        this.postId = offer.getPostId();
    }

    @Override
    public Offer buildEntity() {
        Seller entitySeller = seller != null ? seller.buildEntity() : null;
        return new Offer()
                .setId(id)
                .setSeller(entitySeller)
                .setPostId(postId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
