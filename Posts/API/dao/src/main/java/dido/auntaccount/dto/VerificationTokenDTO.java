package dido.auntaccount.dto;

import dido.auntaccount.entities.VerificationToken;

import java.util.Date;

public class VerificationTokenDTO {

    private String token;
    private Date expirationDate;
    private UserProfileDTO user;

    public VerificationTokenDTO() {
    }

    public VerificationTokenDTO(VerificationToken token) {
        this.token = token.getToken();
        this.expirationDate = token.getExpirationDate();
        this.user = new UserProfileDTO(token.getUser());
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

    public UserProfileDTO getUser() {
        return user;
    }

    public void setUser(UserProfileDTO user) {
        this.user = user;
    }
}
