package dido.auntaccount.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RefreshToken")
public class RefreshToken {

    @Id
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    private Long userId;

    public RefreshToken() {
    }

    public RefreshToken(String token, Date expirationDate, Long userId) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public RefreshToken setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
