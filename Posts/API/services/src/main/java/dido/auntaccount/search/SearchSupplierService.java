package dido.auntaccount.search;

import dido.auntaccount.dto.SupplierDTO;

import java.util.List;

public interface SearchSupplierService {

    String saveSupplier(SupplierDTO supplier);

    SupplierDTO getSupplier(String id);

    List<Long> getSupplierIdsByTags(List<String> tags);

}
