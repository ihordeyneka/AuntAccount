package dido.auntaccount.dto;

import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.User;

import java.util.Date;

public class MessageDTO implements DTO<Message> {

    private Long id;
    private String description;
    private byte[] photo;
    private Date creationDate;
    private UserDTO sender;
    private Long offerId;
    private boolean isRead;

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
        this.isRead = message.isRead();
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
                .setOfferId(offerId)
                .setRead(isRead);
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

    public MessageDTO setDescription(String description) {
        this.description = description;
        return this;
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

    public MessageDTO setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public UserDTO getSender() {
        return sender;
    }

    public MessageDTO setSender(UserDTO sender) {
        this.sender = sender;
        return this;
    }

    public Long getOfferId() {
        return offerId;
    }

    public MessageDTO setOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}
