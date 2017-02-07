package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.MessageDAO;
import dido.auntaccount.dto.MessageDTO;
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
}
