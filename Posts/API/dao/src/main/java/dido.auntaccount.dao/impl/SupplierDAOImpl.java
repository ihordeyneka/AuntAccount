package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.SupplierDAO;
import dido.auntaccount.entities.Supplier;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class SupplierDAOImpl extends GeneralDAO<Supplier> implements SupplierDAO {

    @Inject
    public SupplierDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Supplier find(Long supplierId) {
        return findEntity(supplierId, Supplier.class);
    }

    public Supplier save(Supplier supplier) throws Exception {
        return persistEntity(supplier);
    }

}
