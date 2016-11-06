package dido.auntaccount.search.impl;

import dido.auntaccount.dto.SupplierDTO;
import dido.auntaccount.dto.mapper.JsonMapper;
import dido.auntaccount.search.SearchSupplierService;
import dido.auntaccount.search.client.SearchClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;

import javax.inject.Inject;

public class SearchSupplierServiceImpl implements SearchSupplierService {

    private static final Logger logger = LogManager.getLogger(SearchSupplierServiceImpl.class);

    @Inject
    SearchClientService clientService;

    @Inject
    JsonMapper mapper;

    public SearchSupplierServiceImpl() {

    }

    public String saveSupplier(SupplierDTO supplier) {
        IndexResponse response = clientService.getClient().prepareIndex("suppliers", "supplier")
                .setSource(mapper.supplierDTOToJson(supplier)).get();
        return response.getId();
    }

    public SupplierDTO getSupplier(String id) {
        GetResponse response = clientService.getClient().prepareGet("suppliers", "supplier", id).get();
        byte[] source = response.getSourceAsBytes();
        return source != null ? mapper.jsonToSupplierDTO(source) : null;
    }

}
