package dido.auntaccount.dto;

import dido.auntaccount.entities.Location;
import dido.auntaccount.entities.User;

import java.io.Serializable;
import java.sql.Date;

public class UserDTO implements DTO<User>, Serializable {

    private Long id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private byte[] photo;
    private String website;
    private Date creationDate;
    private Long locationId;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.photo = user.getPhoto();
        this.website = user.getWebsite();
        this.creationDate = user.getCreationDate();
        this.locationId = user.getLocation().getId();
    }

    @Override
    public User buildEntity() {
        Location location = new Location().setId(id);
        return new User()
                .setId(id)
                .setName(name)
                .setPassword(password)
                .setEmail(email)
                .setPhone(phone)
                .setPhoto(photo)
                .setWebsite(website)
                .setCreationDate(creationDate)
                .setLocation(location);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

}
