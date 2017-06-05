package dido.auntaccount.dto;

import java.util.Date;

public class NotificationDTO {

    private Long postId;
    private SellerDTO seller;
    private Date creationDate;
    private String postTags;
    private String description;

    public NotificationDTO() {
    }

    public Long getPostId() {
        return postId;
    }

    public NotificationDTO setPostId(Long postId) {
        this.postId = postId;
        return this;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public NotificationDTO setSeller(SellerDTO seller) {
        this.seller = seller;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public NotificationDTO setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getPostTags() {
        return postTags;
    }

    public NotificationDTO setPostTags(String postTags) {
        this.postTags = postTags;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public NotificationDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}
