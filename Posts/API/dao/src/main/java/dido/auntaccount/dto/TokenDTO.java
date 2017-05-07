package dido.auntaccount.dto;

import dido.auntaccount.entities.Token;

import java.util.Date;

public class TokenDTO {

    private String token;
    private Date expirationDate;
    private Long userId;

    public TokenDTO() {
    }

    public TokenDTO(Token token) {
        this.token = token.getToken();
        this.expirationDate = token.getExpirationDate();
        this.userId = token.getUserId();
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
