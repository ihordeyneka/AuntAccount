package dido.auntaccount.dto;

import dido.auntaccount.entities.Country;

public class CountryDTO implements DTO<Country> {

    private String country;

    public CountryDTO(Country country) {
        this.country = country.getCountry();
    }

    @Override
    public Country buildEntity() {
        return new Country()
                .setCountry(country);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
