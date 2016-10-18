package dido.auntaccount.dao;

import dido.auntaccount.entities.Message;

public interface MessageDAO {

    public Message find(Long id);

    public Message save(Message message) throws Exception;

    public void delete(Message message) throws Exception;

}
