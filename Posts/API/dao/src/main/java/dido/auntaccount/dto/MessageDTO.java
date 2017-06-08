package dido.auntaccount.dto;

import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Date;

public class MessageDTO implements DTO<Message> {

    private static final Logger logger = LogManager.getLogger(MessageDTO.class);

    private Long id;
    private String description;
    private String photo;
    private Date creationDate;
    private UserDTO sender;
    private Long offerId;
    private boolean isRead;

    public MessageDTO() {
    }

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.description = message.getDescription();
        this.photo = encodeImage(message.getPhoto());
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
                .setPhoto(decodeImage(photo))
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

    public String getPhoto() {
        return photo;
    }

    public MessageDTO setPhoto(String photo) {
        this.photo = photo;
        return this;
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

    private String encodeImage(byte[] image) {
        if (image == null) {
            return null;
        }
        String contentType = null;
        try {
            contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(image));
        } catch (IOException e) {
            logger.error("Can't get extension of user's profile picture, userId " + id);
        }
        return "data:image/" + contentType + ";base64," + Base64.encodeBase64String(image);
    }

    private byte[] decodeImage(String image) {
        return image != null ? Base64.decodeBase64(image) : null;
    }

}
