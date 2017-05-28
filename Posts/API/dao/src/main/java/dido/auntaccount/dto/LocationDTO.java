package dido.auntaccount.dto;

import dido.auntaccount.entities.Country;
import dido.auntaccount.entities.Location;

public class LocationDTO implements DTO<Location> {

    private Long id;
    private double latitude;
    private double longitude;
    private String city;
    private String region1;
    private String region2;
    private String name;
    private Integer streetNumber;
    private String route;
    private String neighborhood;
    private CountryDTO country;
    private double radius;

    public LocationDTO() {
    }

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.city = location.getCity();
        this.region1 = location.getRegion1();
        this.region2 = location.getRegion2();
        this.name = location.getName();
        this.neighborhood = location.getNeighborhood();
        this.streetNumber = location.getStreetNumber();
        this.route = location.getRoute();
        Country country = location.getCountry();
        this.country = country != null ? new CountryDTO(country) : null;
        this.radius = location.getRadius();
    }

    @Override
    public Location buildEntity() {
        Country entityCountry = country != null ? country.buildEntity() : null;
        return new Location()
                .setId(id)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .setCity(city)
                .setRegion1(region1)
                .setRegion2(region2)
                .setName(name)
                .setNeighborhood(neighborhood)
                .setRoute(route)
                .setStreetNumber(streetNumber)
                .setCountry(entityCountry)
                .setRadius(radius);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion1() {
        return region1;
    }

    public void setRegion1(String region1) {
        this.region1 = region1;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getRegion2() {
        return region2;
    }

    public void setRegion2(String region2) {
        this.region2 = region2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
}
