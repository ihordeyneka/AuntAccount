package dido.auntaccount.dto;

import dido.auntaccount.entities.Subscription;

public class SubscriptionDTO {


    private String endpoint;

    private String publicKey;

    private String auth;

    private Long userId;

    public SubscriptionDTO(Subscription s) {
        this.endpoint = s.getEndpoint();
        this.publicKey = s.getPublicKey();
        this.auth = s.getAuth();
        this.userId = s.getUserId();
    }

    public Subscription buildEntity() {
        return new Subscription()
                .setEndpoint(endpoint)
                .setPublicKey(publicKey)
                .setAuth(auth)
                .setUserId(userId);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
