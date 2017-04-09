package dido.auntaccount.dto;

import dido.auntaccount.entities.Country;
import dido.auntaccount.entities.Location;

public class LocationDTO implements DTO<Location> {

    private Long id;
    private double latitude;
    private double longitude;
    private String city;
    private String region;
    private String street;
    private CountryDTO country;
    private double radius;

    public LocationDTO() {
    }

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.city = location.getCity();
        this.region = location.getRegion();
        this.street = location.getStreet();
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
                .setRegion(region)
                .setStreet(street)
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet() {
        return street;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
