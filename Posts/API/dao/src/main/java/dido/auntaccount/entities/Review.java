package dido.auntaccount.entities;

import dido.auntaccount.utils.JodaDateTimeConverter;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.DateTime;

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

    @Converter(name = "dateTimeConverter", converterClass = JodaDateTimeConverter.class)
    @Convert("dateTimeConverter")
    private DateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "AuthorId")
    private User author;

    @ManyToOne
    @JoinColumn(name = "ObjectId")
    private User object;

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getObject() {
        return object;
    }

    public void setObject(User object) {
        this.object = object;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }
}
