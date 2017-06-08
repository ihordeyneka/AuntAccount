package dido.auntaccount.service.business;

import dido.auntaccount.dto.MessageDTO;

import java.io.InputStream;

public interface MessageService {

    MessageDTO getMessage(Long id);

    MessageDTO saveMessage(MessageDTO message);

    MessageDTO saveMessage(InputStream uploadedInputStream, Long offerId, Long loggedInUserId);
}
