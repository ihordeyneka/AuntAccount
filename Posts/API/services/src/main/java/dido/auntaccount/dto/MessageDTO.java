package dido.auntaccount.dto;

import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.User;

import java.sql.Date;

public class MessageDTO implements DTO<Message> {

    private Long id;
    private String description;
    private byte[] photo;
    private Date creationDate;
    private Long senderId;
    private Long offerId;

    public MessageDTO() {
    }

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.description = message.getDescription();
        this.photo = message.getPhoto();
        this.creationDate = message.getCreationDate();
        this.senderId = message.getSender().getId();
        this.offerId = message.getOfferId();
    }

    @Override
    public Message buildEntity() {
        User sender = new User().setId(senderId);
        return new Message()
                .setId(id)
                .setDescription(description)
                .setPhoto(photo)
                .setCreationDate(creationDate)
                .setSender(sender)
                .setOfferId(offerId);
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }
}
