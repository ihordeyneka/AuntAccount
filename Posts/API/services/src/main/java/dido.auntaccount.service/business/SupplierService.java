package dido.auntaccount.service.business;

import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.SupplierDTO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Supplier;

import java.util.List;

public interface SupplierService {

    SupplierDTO getSupplier(Long supplierId);

    SupplierDTO saveSupplier(SupplierDTO supplier);

    List<PostDTO> getSupplierPosts(Long supplierId);

    void savePostForSuppliers(PostDTO post);

}
