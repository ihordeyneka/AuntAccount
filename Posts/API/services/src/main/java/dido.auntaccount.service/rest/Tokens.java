package dido.auntaccount.service.rest;

public final class Tokens {

    private final String accessToken;
    private final long accessExpirationDate;
    private final String refreshToken;
    private final long refreshExpirationDate;

    public Tokens(String accessToken, long accessExpirationDate, String refreshToken, long refreshExpirationDate) {
        this.accessToken = accessToken;
        this.accessExpirationDate = accessExpirationDate;
        this.refreshToken = refreshToken;
        this.refreshExpirationDate = refreshExpirationDate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getAccessExpirationDate() {
        return accessExpirationDate;
    }

    public long getRefreshExpirationDate() {
        return refreshExpirationDate;
    }

}
