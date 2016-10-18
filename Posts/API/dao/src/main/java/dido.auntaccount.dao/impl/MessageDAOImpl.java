package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.MessageDAO;
import dido.auntaccount.entities.Message;

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
        return persistEntity(message);
    }

    public void delete(Message message) throws Exception {
        deleteEntity(message.getId(), Message.class);
    }

}
