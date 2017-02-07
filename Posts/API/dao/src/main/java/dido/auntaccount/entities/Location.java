package dido.auntaccount.entities;

import javax.persistence.*;

@Entity
public class Location {

    @Id
    private Long id;
    private double latitude;
    private double longitude;
    private String city;
    private String region;
    private String street;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CountryId")
    private Country country;

    public Long getId() {
        return id;
    }

    public Location setId(Long id) {
        this.id = id;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Location setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Location setCity(String city) {
        this.city = city;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public Location setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Location setStreet(String street) {
        this.street = street;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public Location setCountry(Country country) {
        this.country = country;
        return this;
    }
}
