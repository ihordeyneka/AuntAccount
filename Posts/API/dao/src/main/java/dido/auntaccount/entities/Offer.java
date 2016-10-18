package dido.auntaccount.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Offer {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SupplierId")
    private User supplier;

    @ManyToOne
    @JoinColumn(name = "PostId")
    private Post post;

    @OneToMany(mappedBy = "offer")
    private List<Message> messages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getSupplier() {
        return supplier;
    }

    public void setSupplier(User supplier) {
        this.supplier = supplier;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}
