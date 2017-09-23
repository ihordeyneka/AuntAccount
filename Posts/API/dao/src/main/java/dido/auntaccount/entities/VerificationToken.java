package dido.auntaccount.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class VerificationToken {

    @Id
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "UserId")
    private User user;

    public String getToken() {
        return token;
    }

    public VerificationToken setToken(String token) {
        this.token = token;
        return this;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public VerificationToken setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public User getUser() {
        return user;
    }

    public VerificationToken setUser(User user) {
        this.user = user;
        return this;
    }
}

