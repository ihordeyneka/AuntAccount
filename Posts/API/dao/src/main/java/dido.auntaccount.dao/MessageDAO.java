package dido.auntaccount.dao;

import dido.auntaccount.entities.Message;

/**
 * Created by kho on 07.10.16.
 */
public interface MessageDAO {

    public Message find(Long id);

    public Message save(Message message);

}
