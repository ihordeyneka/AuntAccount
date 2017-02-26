package dido.auntaccount.dto;

import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.User;

import java.sql.Date;

public class MessageDTO implements DTO<Message> {

    private Long id;
    private String description;
    private byte[] photo;
    private Date creationDate;
    private UserDTO sender;
    private Long offerId;

    public MessageDTO() {
    }

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.description = message.getDescription();
        this.photo = message.getPhoto();
        this.creationDate = message.getCreationDate();
        User senderEntity = message.getSender();
        this.sender = senderEntity != null ? new UserDTO(senderEntity) : null;
        this.offerId = message.getOfferId();
    }

    @Override
    public Message buildEntity() {
        User senderEntity = sender != null ? sender.buildEntity() : null;
        return new Message()
                .setId(id)
                .setDescription(description)
                .setPhoto(photo)
                .setCreationDate(creationDate)
                .setSender(senderEntity)
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

    public UserDTO getSender() {
        return sender;
    }

    public void setSender(UserDTO sender) {
        this.sender = sender;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }
}
