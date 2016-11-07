package dido.auntaccount.service.business;

import dido.auntaccount.entities.Message;

public interface MessageService {

    public Message getMessage(Long id);

    public Message saveMessage(Message message);
}
