package dido.auntaccount.dto;

import dido.auntaccount.entities.Location;
import dido.auntaccount.entities.User;

import java.io.Serializable;
import java.sql.Date;

public class UserDTO implements DTO<User>, Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private byte[] photo;
    private Date creationDate;
    private LocationDTO location;
    private String clientId;

    public UserDTO() {
    }

    public UserDTO(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.photo = user.getPhoto();
        this.creationDate = user.getCreationDate();
        Location location = user.getLocation();
        this.location = location != null ? new LocationDTO(user.getLocation()) : null;
        this.clientId = user.getClientId();
    }

    @Override
    public User buildEntity() {
        Location entityLocation = location != null ? location.buildEntity() : null;
        return new User()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setEmail(email)
                .setPhoto(photo)
                .setCreationDate(creationDate)
                .setLocation(entityLocation)
                .setClientId(clientId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
