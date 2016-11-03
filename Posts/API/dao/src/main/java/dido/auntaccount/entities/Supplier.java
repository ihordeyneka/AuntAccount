package dido.auntaccount.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("1")
public class Supplier extends Person {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SupplierTag",
            joinColumns = @JoinColumn(name = "SupplierId"),
            inverseJoinColumns = @JoinColumn(name = "TagId"))
    private List<Tag> supplierTags;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SupplierPost",
            joinColumns = @JoinColumn(name = "SupplierId"),
            inverseJoinColumns = @JoinColumn(name = "PostId"))
    private List<Post> supplierPosts;

    public List<Tag> getSupplierTags() {
        return supplierTags;
    }

    public void setSupplierTags(List<Tag> supplierTags) {
        this.supplierTags = supplierTags;
    }

    public List<Post> getSupplierPosts() {
        return supplierPosts;
    }

    public void setSupplierPosts(List<Post> supplierPosts) {
        this.supplierPosts = supplierPosts;
    }
}
