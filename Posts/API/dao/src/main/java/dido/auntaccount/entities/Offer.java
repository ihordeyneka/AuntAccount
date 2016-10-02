package dido.auntaccount.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Offer {

    @Id
    private Long id;
    private Long postId;
    private Long supplierId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

}
