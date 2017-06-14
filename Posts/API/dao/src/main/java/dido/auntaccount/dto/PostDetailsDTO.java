package dido.auntaccount.dto;

import dido.auntaccount.entities.Location;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Tag;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static dido.auntaccount.dto.TagDTO.parsePostTags;

public class PostDetailsDTO extends PictureDTO implements DTO<Post> {

    private long id;
    private String description;
    private String photo;
    private Double priceMax;
    private Double priceMin;
    private Date creationDate;
    private LocationDTO location;
    private String postTags;
    private UserDTO user;

    public PostDetailsDTO() {
    }

    public PostDetailsDTO(PostDTO post, UserDTO userDTO) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.photo = post.getPhoto();
        this.priceMax = post.getPriceMax();
        this.priceMin = post.getPriceMin();
        this.creationDate = post.getCreationDate();
        this.location = post.getLocation();
        this.postTags = post.getPostTags();
        this.user = userDTO;
    }

    public Post buildEntity() {
        List<Tag> tags = parsePostTags(postTags).stream().map(Tag::new).collect(Collectors.toList());
        Location entityLocation = location.buildEntity();
        return new Post()
                .setId(id)
                .setDescription(description)
                .setPhoto(decodeImage(photo))
                .setPriceMax(priceMax)
                .setPriceMin(priceMin)
                .setCreationDate(creationDate)
                .setLocation(entityLocation)
                .setUserId(user.getId())
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
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

    public List<TagDTO> getTags() {
        return TagDTO.getTags(postTags);
    }
}
