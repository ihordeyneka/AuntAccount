package dido.auntaccount.dto;

import dido.auntaccount.entities.Country;

public class CountryDTO implements DTO<Country> {

    private Long id;
    private String country;

    public CountryDTO(Country country) {
        this.id = country.getId();
        this.country = country.getCountry();
    }

    @Override
    public Country buildEntity() {
        return new Country()
                .setId(id)
                .setCountry(country);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
