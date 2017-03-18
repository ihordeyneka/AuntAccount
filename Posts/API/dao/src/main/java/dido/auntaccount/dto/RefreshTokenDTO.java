package dido.auntaccount.dto;

import dido.auntaccount.entities.RefreshToken;
import org.joda.time.DateTime;

import java.sql.Date;

public class RefreshTokenDTO {

    private String refreshToken;
    private Date expirationDate;

    public RefreshTokenDTO(String refreshToken, Date expirationDate) {
        this.refreshToken = refreshToken;
        this.expirationDate = expirationDate;
    }

    public RefreshTokenDTO(RefreshToken refreshToken) {
        this.refreshToken = refreshToken.getToken();
        this.expirationDate = refreshToken.getExpirationDate();
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public boolean isValid() {
        long now = DateTime.now().getMillis();
        return expirationDate.before(new Date(now));
    }
}
