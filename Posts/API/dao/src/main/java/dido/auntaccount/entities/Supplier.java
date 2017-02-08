package dido.auntaccount.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "Id")
    @OneToOne(cascade = CascadeType.ALL)
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

    public Supplier setSupplierTags(List<Tag> supplierTags) {
        this.supplierTags = supplierTags;
        return this;
    }

    public List<Post> getSupplierPosts() {
        return supplierPosts;
    }

    public Supplier setSupplierPosts(List<Post> supplierPosts) {
        this.supplierPosts = supplierPosts;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Supplier setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Supplier setUser(User user) {
        this.user = user;
        return this;
    }
}
