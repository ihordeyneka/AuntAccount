package dido.auntaccount.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class RefreshToken {

    @Id
    private String token;

    private Date expirationDate;

    public RefreshToken() {
    }

    public RefreshToken(String token, Date expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
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

}
