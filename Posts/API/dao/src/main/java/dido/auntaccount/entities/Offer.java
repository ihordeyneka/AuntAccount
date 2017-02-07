package dido.auntaccount.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Offer {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "SupplierId")
    private Supplier supplier;

    private Long postId;

    public Long getId() {
        return id;
    }

    public Offer setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getPostId() {
        return postId;
    }

    public Offer setPostId(Long postId) {
        this.postId = postId;
        return this;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Offer setSupplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }

}
