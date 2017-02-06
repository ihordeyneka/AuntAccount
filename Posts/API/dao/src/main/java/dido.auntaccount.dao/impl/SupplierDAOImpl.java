package dido.auntaccount.dao.impl;

import dido.auntaccount.dao.GeneralDAO;
import dido.auntaccount.dao.SupplierDAO;
import dido.auntaccount.entities.Post;
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

    public void addSupplierPost(Long supplierId, Post post) {
        Supplier supplier = find(supplierId);
        supplier.getSupplierPosts().add(post);
        try {
            updateEntity(supplier);
        } catch (Exception e) {
            throw new IllegalStateException("Couldn't save post for supplier");
        }
    }

}
