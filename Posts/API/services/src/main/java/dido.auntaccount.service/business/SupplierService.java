package dido.auntaccount.service.business;

import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Supplier;

import java.util.List;

public interface SupplierService {

    Supplier getSupplier(Long supplierId);

    Supplier saveSupplier(Supplier supplier);

    List<Post> getSupplierPosts(Long supplierId);

    void savePostForSuppliers(Post post);
}
