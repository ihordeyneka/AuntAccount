package dido.auntaccount.search;

import dido.auntaccount.dto.SupplierDTO;

public interface SearchSupplierService {

    String saveSupplier(SupplierDTO supplier);

    SupplierDTO getSupplier(String id);

}
