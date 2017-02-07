package dido.auntaccount.dto;

import dido.auntaccount.entities.Review;

import java.sql.Date;

public class ReviewDTO {

    private Long id;
    private String description;
    private int rate;
    private Date creationDate;
    private Long authorId;
    private Long objectId;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.description = review.getDescription();
        this.rate = review.getRate();
        this.creationDate = review.getCreationDate();
        this.authorId = review.getAuthor().getId();
        this.objectId = review.getObjectId();
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
