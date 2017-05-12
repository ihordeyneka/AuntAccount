package dido.auntaccount.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long userId;
    private String phone;
    private byte[] photo;
    private String website;
    private double rate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LocationId")
    private Location location;

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

    public String getName() {
        return name;
    }

    public Supplier setName(String name) {
        this.name = name;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Supplier setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Supplier setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public Supplier setWebsite(String website) {
        this.website = website;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Supplier setLocation(Location location) {
        this.location = location;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Supplier setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Supplier setPhoto(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public Supplier setRate(double rate) {
        this.rate = rate;
        return this;
    }
}
