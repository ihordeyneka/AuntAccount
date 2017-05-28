package dido.auntaccount.dto;

import dido.auntaccount.entities.Location;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Tag;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static dido.auntaccount.dto.TagDTO.parsePostTags;

public class PostDTO implements DTO<Post> {

    private long id;
    private String description;
    private byte[] photo;
    private Double priceMax;
    private Double priceMin;
    private Date creationDate;
    private LocationDTO location;
    private String postTags;
    private Long userId;
    private int offerCount;

    public PostDTO() {
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.photo = post.getPhoto();
        this.priceMax = post.getPriceMax();
        this.priceMin = post.getPriceMin();
        this.creationDate = post.getCreationDate();
        Location location = post.getLocation();
        this.location = location != null ? new LocationDTO(location) : null;
        this.postTags = post.getPostTags().stream().map(Tag::getTag).collect(Collectors.joining(", "));
        this.userId = post.getUserId();
    }

    public PostDTO(Post post, Integer offerCount) {
        this(post);
        this.offerCount = offerCount;
    }

    public Post buildEntity() {
        List<Tag> tags = parsePostTags(postTags).stream().map(Tag::new).collect(Collectors.toList());
        Location entityLocation = location.buildEntity();
        return new Post()
                .setId(id)
                .setDescription(description)
                .setPhoto(photo)
                .setPriceMax(priceMax)
                .setPriceMin(priceMin)
                .setCreationDate(creationDate)
                .setLocation(entityLocation)
                .setUserId(userId)
                .setPostTags(tags);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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

    public String getPostTags() {
        return postTags;
    }

    public void setPostTags(String postTags) {
        this.postTags = postTags;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public int getOfferCount() {
        return offerCount;
    }

    public void setOfferCount(int offerCount) {
        this.offerCount = offerCount;
    }

    public List<TagDTO> getTags() {
        return TagDTO.getTags(postTags);
    }
}
