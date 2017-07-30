package dido.auntaccount.entities;

import javax.persistence.*;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double latitude;
    private double longitude;
    private String city;
    private String region1;
    private String region2;
    private String name;
    private String place;
    private String placeId;
    private String route;
    private String neighborhood;
    private Integer streetNumber;
    private double radius;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Country")
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

    public String getRegion1() {
        return region1;
    }

    public Location setRegion1(String region1) {
        this.region1 = region1;
        return this;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public Location setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public Location setCountry(Country country) {
        this.country = country;
        return this;
    }

    public double getRadius() {
        return radius;
    }

    public Location setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public String getRegion2() {
        return region2;
    }

    public Location setRegion2(String region2) {
        this.region2 = region2;
        return this;
    }

    public String getName() {
        return name;
    }

    public Location setName(String name) {
        this.name = name;
        return this;
    }

    public String getRoute() {
        return route;
    }

    public Location setRoute(String route) {
        this.route = route;
        return this;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public Location setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }

    public String getPlace() {
        return place;
    }

    public Location setPlace(String place) {
        this.place = place;
        return this;
    }

    public String getPlaceId() {
        return placeId;
    }

    public Location setPlaceId(String placeId) {
        this.placeId = placeId;
        return this;
    }
}
