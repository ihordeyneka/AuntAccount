package dido.auntaccount.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String description;
    private int rate;

    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AuthorId")
    private User author;

    private Long objectId;

    public Long getId() {
        return id;
    }

    public Review setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Review setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getRate() {
        return rate;
    }

    public Review setRate(int rate) {
        this.rate = rate;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public Review setAuthor(User author) {
        this.author = author;
        return this;
    }

    public Long getObjectId() {
        return objectId;
    }

    public Review setObjectId(Long objectId) {
        this.objectId = objectId;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Review setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
