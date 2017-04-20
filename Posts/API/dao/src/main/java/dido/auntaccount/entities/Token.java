package dido.auntaccount.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Token {

    @Id
    private String token;
    private Date expirationDate;
    private Long userId;

    public Token() {
    }

    public Token(String token, Date expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public Token(String token, Date expirationDate, Long userId) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public Token setToken(String token) {
        this.token = token;
        return this;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Token setExpirationDate(Date expirationDate) {
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
