package dido.auntaccount.service.business;

import dido.auntaccount.dto.MessageDTO;

public interface MessageService {

    public MessageDTO getMessage(Long id);

    public MessageDTO saveMessage(MessageDTO message);
}
