package dido.auntaccount.entities;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Review {

    @Id
    private Long id;
    private String description;
    private int rate;
    private DateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "AuthorId", referencedColumnName = "Id")
    private User authorId;

    @ManyToOne
    @JoinColumn(name = "ObjectId", referencedColumnName = "Id")
    private User objectId;

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

    public User getAuthorId() {
        return authorId;
    }

    public void setAuthorId(User authorId) {
        this.authorId = authorId;
    }

    public User getObjectId() {
        return objectId;
    }

    public void setObjectId(User objectId) {
        this.objectId = objectId;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }
}
