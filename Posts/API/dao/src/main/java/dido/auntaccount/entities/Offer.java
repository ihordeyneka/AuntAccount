package dido.auntaccount.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Offer {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SupplierId", referencedColumnName = "Id")
    private User supplier;

    @ManyToOne
    @JoinColumn(name = "PostId", referencedColumnName = "Id")
    private Post post;

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
