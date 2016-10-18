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
public class Message {

    @Id
    private Long id;
    private String description;
    private byte[] photo;

    @Converter(name = "dateTimeConverter", converterClass = JodaDateTimeConverter.class)
    @Convert("dateTimeConverter")
    private DateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "SenderId")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "OfferId")
    private Offer offer;

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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
