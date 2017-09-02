package dido.auntaccount.dto;

import dido.auntaccount.entities.Country;
import dido.auntaccount.entities.Location;

public class LocationDTO implements DTO<Location> {

    private Long id;
    private String city;
    private String region1;
    private String region2;
    private String place;
    private String placeId;
    private String name;
    private Integer streetNumber;
    private String route;
    private String neighborhood;
    private CountryDTO country;
    private double radius;
    private PointDTO point;

    public LocationDTO() {
    }

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.point = new PointDTO(location.getLatitude(), location.getLongitude());
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
        this.place = location.getPlace();
        this.placeId = location.getPlaceId();
    }

    @Override
    public Location buildEntity() {
        Country entityCountry = country != null ? country.buildEntity() : null;
        return new Location()
                .setId(id)
                .setLatitude(point.getLat())
                .setLongitude(point.getLon())
                .setCity(city)
                .setRegion1(region1)
                .setRegion2(region2)
                .setName(name)
                .setNeighborhood(neighborhood)
                .setRoute(route)
                .setStreetNumber(streetNumber)
                .setCountry(entityCountry)
                .setRadius(radius)
                .setPlace(place)
                .setPlaceId(placeId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public PointDTO getPoint() {
        return point;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
