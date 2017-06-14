package dido.auntaccount.dto;

import java.util.List;

public final class OfferMessagesDTO {

    private final PostDetailsDTO post;
    private final List<MessageDTO> messages;

    public OfferMessagesDTO(PostDetailsDTO post, List<MessageDTO> messages) {
        this.post = post;
        this.messages = messages;
    }

    public PostDetailsDTO getPost() {
        return post;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

}
