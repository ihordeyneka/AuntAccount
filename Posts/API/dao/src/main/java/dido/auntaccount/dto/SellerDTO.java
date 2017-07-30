package dido.auntaccount.dto;

import dido.auntaccount.entities.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SellerDTO extends PictureDTO implements DTO<Seller> {

    private Long id;
    private String name;
    private Long userId;
    private String phone;
    private String photo;
    private String website;
    private double averageRate;
    private Date creationDate;
    private LocationDTO location;
    private String tags;
    private List<PostDTO> posts = new ArrayList<>();
    private List<String> tagList;

    public SellerDTO() {
    }

    @Override
    public Seller buildEntity() {
        Location entityLocation = location != null ? location.buildEntity() : null;
        Seller entitySeller = new Seller()
                .setId(id)
                .setName(name)
                .setUserId(userId)
                .setPhone(phone)
                .setWebsite(website)
                .setPhoto(decodeImage(photo))
                .setRate(averageRate)
                .setCreationDate(creationDate)
                .setLocation(entityLocation);
        List<Tag> entityTags = TagDTO.parsePostTags(tags).stream().map(Tag::new).collect(Collectors.toList());
        List<Post> entityPosts = posts.stream().map(PostDTO::buildEntity).collect(Collectors.toList());
        return entitySeller
                .setSellerTags(entityTags)
                .setSellerPosts(entityPosts);
    }

    public SellerDTO(Seller seller) {
        this.id = seller.getId();
        this.tagList = seller.getSellerTags().stream().map(Tag::getTag).collect(Collectors.toList());
        this.tags = this.tagList.stream().collect(Collectors.joining(", "));
        this.name = seller.getName();
        this.userId = seller.getUserId();
        this.photo = encodeImage(seller.getPhoto());
        this.phone = seller.getPhone();
        this.creationDate = seller.getCreationDate();
        this.averageRate = seller.getRate();
        Location location = seller.getLocation();
        this.location = location != null ? new LocationDTO(location) : null;
        this.website = seller.getWebsite();
        this.posts = seller.getSellerPosts().stream().map(PostDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public SellerDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
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

    public double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
