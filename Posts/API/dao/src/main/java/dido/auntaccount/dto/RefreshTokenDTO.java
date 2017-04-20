package dido.auntaccount.dto;

import dido.auntaccount.entities.RefreshToken;
import org.joda.time.DateTime;

import java.sql.Date;

public class RefreshTokenDTO {

    private String refreshToken;
    private Date expirationDate;
    private Long userId;

    public RefreshTokenDTO(String refreshToken, Date expirationDate, Long userId) {
        this.refreshToken = refreshToken;
        this.expirationDate = expirationDate;
        this.userId = userId;
    }

    public RefreshTokenDTO(RefreshToken refreshToken) {
        this.refreshToken = refreshToken.getToken();
        this.expirationDate = refreshToken.getExpirationDate();
        this.userId = refreshToken.getUserId();
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isValid() {
        long now = DateTime.now().getMillis();
        return expirationDate.before(new Date(now));
    }
}
