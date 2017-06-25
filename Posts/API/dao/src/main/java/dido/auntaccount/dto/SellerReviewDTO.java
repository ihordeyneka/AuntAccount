package dido.auntaccount.dto;

import dido.auntaccount.entities.SellerReview;
import dido.auntaccount.entities.User;

import java.sql.Date;

public class SellerReviewDTO implements DTO<SellerReview> {

    private Long id;
    private String description;
    private int rate;
    private Date creationDate;
    private Long userId;
    private Long sellerId;

    public SellerReviewDTO() {
    }

    public SellerReviewDTO(SellerReview review) {
        this.id = review.getId();
        this.description = review.getDescription();
        this.rate = review.getRate();
        this.creationDate = review.getCreationDate();
        this.userId = review.getUserId();
        this.sellerId = review.getSellerId();
    }

    @Override
    public SellerReview buildEntity() {
        User author = new User().setId(userId);
        return new SellerReview()
                .setId(id)
                .setDescription(description)
                .setRate(rate)
                .setCreationDate(creationDate)
                .setUserId(userId)
                .setSellerId(sellerId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
