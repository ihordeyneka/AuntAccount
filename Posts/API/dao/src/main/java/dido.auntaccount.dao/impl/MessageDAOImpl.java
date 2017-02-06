package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.MessageDAO;
import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class MessageDAOImpl extends GeneralDAO<Message> implements MessageDAO {

    @Inject
    public MessageDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Message find(Long id) {
        return findEntity(id, Message.class);
    }

    public Message save(Message message) throws Exception {
        Long senderId = message.getSender().getId();
        User sender = entityManager.getReference(User.class, senderId);
        message.setSender(sender);
        return persistEntity(message);
    }

    public void delete(Message message) throws Exception {
        deleteEntity(message.getId(), Message.class);
    }

}
