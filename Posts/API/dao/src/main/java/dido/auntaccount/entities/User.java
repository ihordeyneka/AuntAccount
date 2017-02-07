package dido.auntaccount.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="USER")
public class User implements Serializable {

    @Id
    private Long id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private byte[] photo;
    private String website;
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LocationId")
    private Location location;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public User setPhoto(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public User setWebsite(String website) {
        this.website = website;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public User setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public User setLocation(Location location) {
        this.location = location;
        return this;
    }

}
