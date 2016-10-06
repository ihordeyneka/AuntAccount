package dido.auntaccount.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Offer {

    @Id
    private Long id;
    private Post post;
    private User supplier;

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
}
