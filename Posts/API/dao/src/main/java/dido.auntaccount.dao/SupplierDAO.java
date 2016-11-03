package dido.auntaccount.dao;

import dido.auntaccount.entities.Supplier;

public interface SupplierDAO {

    public Supplier find(Long supplierId);

    public Supplier save(Supplier supplier) throws Exception;

}
