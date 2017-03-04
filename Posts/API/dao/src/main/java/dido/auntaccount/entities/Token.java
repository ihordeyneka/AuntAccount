package dido.auntaccount.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Token {

    @Id
    private String token;
    private Date expirationDate;

    public Token() {
    }

    public Token(String token, Date expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
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

}