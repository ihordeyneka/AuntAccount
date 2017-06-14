package dido.auntaccount.dto;

import org.apache.commons.codec.binary.Base64;
import dido.auntaccount.entities.Location;
import dido.auntaccount.entities.Seller;
import dido.auntaccount.entities.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLConnection;
import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

public class UserProfileDTO extends PictureDTO implements DTO<User>, Serializable  {

    private static final Logger logger = LogManager.getLogger(UserProfileDTO.class);

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String photo;
    private Date creationDate;
    private LocationDTO location;
    private String clientId;
    private List<SellerDTO> sellers;

    public UserProfileDTO() {
    }

    public UserProfileDTO(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public UserProfileDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.photo = encodeImage(user.getPhoto());
        this.creationDate = user.getCreationDate();
        Location location = user.getLocation();
        this.location = location != null ? new LocationDTO(user.getLocation()) : null;
        this.clientId = user.getClientId();
        final List<Seller> sellerList = user.getSellers();
        this.sellers = sellerList != null ? sellerList.stream().map(SellerDTO::new).collect(Collectors.toList()) : null;
    }

    @Override
    public User buildEntity() {
        Location entityLocation = location != null ? location.buildEntity() : null;
        List<Seller> sellerList = sellers != null ? sellers.stream().map(SellerDTO::buildEntity).collect(Collectors.toList()) : null;
        return new User()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setEmail(email)
                .setPhoto(decodeImage(photo))
                .setCreationDate(creationDate)
                .setLocation(entityLocation)
                .setClientId(clientId)
                .setSellers(sellerList);
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
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

    public List<SellerDTO> getSellers() {
        return sellers;
    }

    public void setSellers(List<SellerDTO> sellers) {
        this.sellers = sellers;
    }

}
