package dido.auntaccount.dto;

import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Seller;

public class OfferDTO implements DTO<Offer> {

    private Long id;
    private SellerDTO seller;
    private Long postId;
    private Integer replyCount;

    public OfferDTO() {
    }

    public OfferDTO(Offer offer) {
        this.id = offer.getId();
        Seller entitySeller = offer.getSeller();
        this.seller = entitySeller != null ? new SellerDTO(entitySeller) : null;
        this.postId = offer.getPostId();
    }


    public OfferDTO(Offer offer, Integer replyCount) {
        this(offer);
        this.replyCount = replyCount;
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

    public OfferDTO setPostId(Long postId) {
        this.postId = postId;
        return this;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public OfferDTO setSeller(SellerDTO seller) {
        this.seller = seller;
        return this;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }
}
