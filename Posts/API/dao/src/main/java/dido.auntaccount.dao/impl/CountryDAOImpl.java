package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.CountryDAO;
import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.entities.Country;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CountryDAOImpl extends GeneralDAO<Country> implements CountryDAO {

    @Inject
    public CountryDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Country find(Long id) {
        return findEntity(id, Country.class);
    }

    @Override
    public Country find(String country) {
        TypedQuery<Country> query = entityManager.createQuery("SELECT c FROM Country c WHERE c.country = :country", Country.class);
        query.setParameter("country", country);
        return getSingleResultOrNull(query);
    }

    @Override
    public Country save(Country country) throws Exception {
        return persistEntity(country);
    }

    @Override
    public void delete(Country country) throws Exception {
        deleteEntity(country.getId(), Country.class);
    }
}
