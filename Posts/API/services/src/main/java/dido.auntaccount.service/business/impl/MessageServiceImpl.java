package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.MessageDAO;
import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.dto.MessageDTO;
import dido.auntaccount.dto.UserDTO;
import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.User;
import dido.auntaccount.service.business.MessageService;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LogManager.getLogger(MessageServiceImpl.class);

    @Inject
    private MessageDAO messageDAO;

    @Inject
    private UserDAO userDAO;


    @Override
    public MessageDTO getMessage(Long id) {
        Message message = messageDAO.find(id);
        return new MessageDTO(message);
    }

    @Override
    public MessageDTO saveMessage(MessageDTO message) {
        Message savedMessage = null;
        try {
            savedMessage = messageDAO.save(message.buildEntity());
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save message", e);
        }
        return new MessageDTO(savedMessage);
    }

    @Override
    public MessageDTO saveMessage(InputStream uploadedInputStream, Long offerId, Long loggedInUserId) {
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(uploadedInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User sender = userDAO.find(loggedInUserId);
        Message message = new Message()
                .setCreationDate(DateTime.now().toDate())
                .setOfferId(offerId)
                .setSender(sender)
                .setPhoto(bytes);
        Message savedMessage = null;
        try {
            savedMessage = messageDAO.save(message);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save message", e);
        }
        return new MessageDTO(savedMessage);
    }
}
