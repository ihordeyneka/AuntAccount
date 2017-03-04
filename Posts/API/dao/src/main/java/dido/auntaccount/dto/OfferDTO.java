package dido.auntaccount.dto;

import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Supplier;

public class OfferDTO implements DTO<Offer> {

    private Long id;
    private Long supplierId;
    private Long postId;
    private Integer replyCount;

    public OfferDTO() {
    }

    public OfferDTO(Offer offer) {
        this.id = offer.getId();
        this.supplierId = offer.getSupplier().getId();
        this.postId = offer.getPostId();
    }


    public OfferDTO(Offer offer, Integer replyCount) {
        this(offer);
        this.replyCount = replyCount;
    }

    @Override
    public Offer buildEntity() {
        Supplier supplier = new Supplier().setId(supplierId);
        return new Offer()
                .setId(id)
                .setSupplier(supplier)
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }
}
