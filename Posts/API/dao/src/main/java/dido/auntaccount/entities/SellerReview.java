package dido.auntaccount.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="SellerReview")
public class SellerReview {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String description;
    private int rate;

    private Date creationDate;

    private Long userId;

    private Long sellerId;

    public Long getId() {
        return id;
    }

    public SellerReview setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SellerReview setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getRate() {
        return rate;
    }

    public SellerReview setRate(int rate) {
        this.rate = rate;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public SellerReview setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public SellerReview setSellerId(Long sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public SellerReview setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
