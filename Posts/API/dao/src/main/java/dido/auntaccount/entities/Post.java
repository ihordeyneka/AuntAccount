package dido.auntaccount.entities;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.*;
import java.util.List;

@Entity
public class Post {

    @Id
    private long id;
    private String description;
    private byte[] photo;
    private Double latitude;
    private Double longitude;
    private Double priceMax;
    private Double priceMin;
    private DateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "LocationId", referencedColumnName = "Id")
    private Location location;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PostTag",
            joinColumns = @JoinColumn(name = "PostId"),
            inverseJoinColumns = @JoinColumn(name = "TagId"))
    private List<Tag> postTags;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "Id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Offer> offers;

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

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public List<Tag> getPostTags() {
        return postTags;
    }

    public void setPostTags(List<Tag> postTags) {
        this.postTags = postTags;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
