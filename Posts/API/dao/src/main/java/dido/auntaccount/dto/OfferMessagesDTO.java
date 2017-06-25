package dido.auntaccount.dto;

import java.util.List;

public final class OfferMessagesDTO {

    private final PostDetailsDTO post;
    private final SellerDTO seller;
    private final List<MessageDTO> messages;

    public OfferMessagesDTO(SellerDTO seller, PostDetailsDTO post, List<MessageDTO> messages) {
        this.post = post;
        this.messages = messages;
        this.seller = seller;
    }

    public PostDetailsDTO getPost() {
        return post;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public SellerDTO getSeller() {
        return seller;
    }

}
