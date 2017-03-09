package dido.auntaccount.dto;

import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Supplier;

public class OfferDTO implements DTO<Offer> {

    private Long id;
    private SupplierDTO supplier;
    private Long postId;
    private Integer replyCount;

    public OfferDTO() {
    }

    public OfferDTO(Offer offer) {
        this.id = offer.getId();
        Supplier entitySupplier = offer.getSupplier();
        this.supplier = entitySupplier != null ? new SupplierDTO(entitySupplier) : null;
        this.postId = offer.getPostId();
    }


    public OfferDTO(Offer offer, Integer replyCount) {
        this(offer);
        this.replyCount = replyCount;
    }

    @Override
    public Offer buildEntity() {
        Supplier entitySupplier = supplier != null ? supplier.buildEntity() : null;
        return new Offer()
                .setId(id)
                .setSupplier(entitySupplier)
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

    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }
}
