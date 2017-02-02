package dido.auntaccount.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Supplier {

    @Id
    private Long id;

    @JoinColumn(name = "Id")
    @OneToOne
    @MapsId
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SupplierTag",
            joinColumns = @JoinColumn(name = "SupplierId"),
            inverseJoinColumns = @JoinColumn(name = "TagId"))
    private List<Tag> supplierTags;

    @ManyToMany(fetch = FetchType.EAGER)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
