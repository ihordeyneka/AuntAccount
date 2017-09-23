package dido.auntaccount.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private byte[] photo;
    private Date creationDate;
    private String clientId;
    private boolean enabled;

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

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String name) {
        this.firstName = name;
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

    public byte[] getPhoto() {
        return photo;
    }

    public User setPhoto(byte[] photo) {
        this.photo = photo;
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

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public User setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
