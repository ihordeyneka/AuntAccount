package dido.auntaccount.dto;

import dido.auntaccount.entities.Token;

import java.sql.Date;

public class TokenDTO {

    private String token;
    private Date expirationDate;

    public TokenDTO() {
    }

    public TokenDTO(Token token) {
        this.token = token.getToken();
        this.expirationDate = token.getExpirationDate();
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

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

}
