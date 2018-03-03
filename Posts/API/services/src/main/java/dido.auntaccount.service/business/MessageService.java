package dido.auntaccount.service.business;

import dido.auntaccount.dto.MessageDTO;
import dido.auntaccount.entities.Message;

import java.io.InputStream;

public interface MessageService {

    MessageDTO getMessage(Long id);

    MessageDTO saveMessage(MessageDTO message);

    MessageDTO saveMessage(InputStream uploadedInputStream, Long offerId, Long loggedInUserId);

    MessageDTO saveMessage(Message message);
}
