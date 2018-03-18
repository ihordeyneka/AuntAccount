package dido.auntaccount.entities;

import javax.persistence.*;

@Entity
@Table(name="Subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String endpoint;

    private String publicKey;

    private String auth;

    private Long userId;

    public Long getId() {
        return id;
    }

    public Subscription setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Subscription setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public Subscription setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public String getAuth() {
        return auth;
    }

    public Subscription setAuth(String auth) {
        this.auth = auth;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Subscription setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}
