package dido.auntaccount.dto;

import dido.auntaccount.entities.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierDTO implements DTO<Supplier> {

    private Long id;
    private String name;
    private Long userId;
    private String phone;
    private byte[] photo;
    private String website;
    private double rate;
    private Date creationDate;
    private LocationDTO location;
    private List<TagDTO> tags;
    private List<PostDTO> posts;

    public SupplierDTO() {
    }

    @Override
    public Supplier buildEntity() {
        Location entityLocation = location != null ? location.buildEntity() : null;
        Supplier entitySupplier = new Supplier()
                .setId(id)
                .setName(name)
                .setUserId(userId)
                .setPhone(phone)
                .setWebsite(website)
                .setPhoto(photo)
                .setRate(rate)
                .setCreationDate(creationDate)
                .setLocation(entityLocation);
        List<Tag> entityTags = tags.stream().map(TagDTO::buildEntity).collect(Collectors.toList());
        List<Post> entityPosts = posts.stream().map(PostDTO::buildEntity).collect(Collectors.toList());
        return entitySupplier
                .setSupplierTags(entityTags)
                .setSupplierPosts(entityPosts);
    }

    public SupplierDTO(Supplier supplier) {
        this.id = supplier.getId();
        this.tags = supplier.getSupplierTags().stream().map(TagDTO::new).collect(Collectors.toList());
        this.name = supplier.getName();
        this.userId = supplier.getUserId();
        this.photo = supplier.getPhoto();
        this.phone = supplier.getPhone();
        this.creationDate = supplier.getCreationDate();
        this.rate = supplier.getRate();
        Location location = supplier.getLocation();
        this.location = location != null ? new LocationDTO(location) : null;
        this.website = supplier.getWebsite();
        this.posts = supplier.getSupplierPosts().stream().map(PostDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
