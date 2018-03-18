package dido.auntaccount.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Seller")
public class Seller {

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
    @JoinTable(name = "SellerTag",
            joinColumns = @JoinColumn(name = "SellerId"),
            inverseJoinColumns = @JoinColumn(name = "TagId"))
    private List<Tag> sellerTags;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SellerPost",
            joinColumns = @JoinColumn(name = "SellerId"),
            inverseJoinColumns = @JoinColumn(name = "PostId"))
    private List<Post> sellerPosts;

    public List<Tag> getSellerTags() {
        return sellerTags;
    }

    public Seller setSellerTags(List<Tag> sellerTags) {
        this.sellerTags = sellerTags;
        return this;
    }

    public List<Post> getSellerPosts() {
        return sellerPosts;
    }

    public Seller setSellerPosts(List<Post> sellerPosts) {
        this.sellerPosts = sellerPosts;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Seller setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Seller setName(String name) {
        this.name = name;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Seller setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Seller setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public Seller setWebsite(String website) {
        this.website = website;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Seller setLocation(Location location) {
        this.location = location;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Seller setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Seller setPhoto(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public Seller setRate(double rate) {
        this.rate = rate;
        return this;
    }
}
