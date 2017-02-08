package dido.auntaccount.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String description;
    private byte[] photo;
    private Double priceMax;
    private Double priceMin;

    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LocationId")
    private Location location;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PostTag",
            joinColumns = @JoinColumn(name = "PostId"),
            inverseJoinColumns = @JoinColumn(name = "TagId"))
    private List<Tag> postTags;

    private Long userId;

    public String getDescription() {
        return description;
    }

    public Post setDescription(String description) {
        this.description = description;
        return this;
    }

    public long getId() {
        return id;
    }

    public Post setId(long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Post setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Post setPhoto(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public Post setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
        return this;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public Post setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
        return this;
    }

    public List<Tag> getPostTags() {
        return postTags;
    }

    public Post setPostTags(List<Tag> postTags) {
        this.postTags = postTags;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Post setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Post setLocation(Location location) {
        this.location = location;
        return this;
    }

}
