package dido.auntaccount.dto;

import dido.auntaccount.entities.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierDTO implements DTO<Supplier> {

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private byte[] photo;
    private String website;
    private Date creationDate;
    private LocationDTO location;
    private List<TagDTO> tags;
    private List<PostDTO> posts;

    public SupplierDTO() {
    }

    @Override
    public Supplier buildEntity() {
        Location entityLocation = location != null ? location.buildEntity() : null;
        User entityUser = new User()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setEmail(email)
                .setPhone(phone)
                .setPhoto(photo)
                .setWebsite(website)
                .setCreationDate(creationDate)
                .setLocation(entityLocation);
        List<Tag> entityTags = tags.stream().map(TagDTO::buildEntity).collect(Collectors.toList());
        List<Post> entityPosts = posts.stream().map(PostDTO::buildEntity).collect(Collectors.toList());
        return new Supplier()
                .setId(id)
                .setUser(entityUser)
                .setSupplierTags(entityTags)
                .setSupplierPosts(entityPosts);
    }

    public SupplierDTO(Supplier supplier) {
        User user = supplier.getUser();
        this.id = user.getId();
        this.tags = supplier.getSupplierTags().stream().map(TagDTO::new).collect(Collectors.toList());
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.photo = user.getPhoto();
        this.website = user.getWebsite();
        this.creationDate = user.getCreationDate();
        Location location = user.getLocation();
        this.location = location != null ? new LocationDTO(user.getLocation()) : null;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
