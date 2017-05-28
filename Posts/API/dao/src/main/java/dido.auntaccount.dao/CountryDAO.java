package dido.auntaccount.dao;

import dido.auntaccount.entities.Country;

public interface CountryDAO {

    public Country find(Long id);

    public Country find(String country);

    public Country save(Country country) throws Exception;

    public void delete(Country country) throws Exception;

}
