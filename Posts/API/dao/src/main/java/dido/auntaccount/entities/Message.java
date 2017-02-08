package dido.auntaccount.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String description;
    private byte[] photo;

    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SenderId")
    private User sender;

    private Long offerId;

    public Long getId() {
        return id;
    }

    public Message setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Message setDescription(String description) {
        this.description = description;
        return this;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Message setPhoto(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Message setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public User getSender() {
        return sender;
    }

    public Message setSender(User sender) {
        this.sender = sender;
        return this;
    }

    public Long getOfferId() {
        return offerId;
    }

    public Message setOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

}
