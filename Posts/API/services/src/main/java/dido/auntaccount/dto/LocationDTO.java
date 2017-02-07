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
    private Long countryId;

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.city = location.getCity();
        this.region = location.getRegion();
        this.street = location.getStreet();
        this.countryId = location.getCountry().getId();
    }

    @Override
    public Location buildEntity() {
        Country country = new Country().setId(countryId);
        return new Location()
                .setId(id)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .setCity(city)
                .setRegion(region)
                .setStreet(street)
                .setCountry(country);
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

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long country) {
        this.countryId = countryId;
    }
}
