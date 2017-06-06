package dido.auntaccount.dao;

import dido.auntaccount.entities.Message;

public interface MessageDAO {

    Message find(Long id);

    Message save(Message message) throws Exception;

    void delete(Message message) throws Exception;

}
