package dido.auntaccount.dao;

import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Supplier;

public interface SupplierDAO {

    public Supplier find(Long supplierId);

    public Supplier save(Supplier supplier) throws Exception;

    public void addSupplierPost(Long supplierId, Post post);

}
