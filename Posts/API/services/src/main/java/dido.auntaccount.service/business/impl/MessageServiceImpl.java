package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.MessageDAO;
import dido.auntaccount.entities.Message;
import dido.auntaccount.service.business.MessageService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LogManager.getLogger(MessageServiceImpl.class);

    @Inject
    private MessageDAO messageDAO;

    @Override
    public Message getMessage(Long id) {
        return messageDAO.find(id);
    }

    @Override
    public Message saveMessage(Message message) {
        Message savedMessage = null;
        try {
            savedMessage = messageDAO.save(message);
        } catch (Exception e) {
           logger.log(Level.ERROR, "Couldn't save message", e);
        }
        return savedMessage;
    }
}
