package dido.auntaccount.search.impl;

import dido.auntaccount.dto.SupplierDTO;
import dido.auntaccount.mappers.JsonMapper;
import dido.auntaccount.search.SearchSupplierService;
import dido.auntaccount.search.client.SearchClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchSupplierServiceImpl implements SearchSupplierService {

    private static final Logger logger = LogManager.getLogger(SearchSupplierServiceImpl.class);
    public static final String SUPPLIER_TYPE = "supplier";
    public static final String INDEX = "dido";
    public static final String TAGS_FIELD = "tags";
    public static final String ID_FIELD = "id";

    @Inject
    SearchClientService clientService;

    @Inject
    JsonMapper mapper;

    public SearchSupplierServiceImpl() {

    }

    public String saveSupplier(SupplierDTO supplier) {
        IndexResponse response = clientService.getClient().prepareIndex(INDEX, SUPPLIER_TYPE, supplier.getId().toString())
                .setSource(mapper.supplierDTOToJson(supplier)).get();
        return response.getId();
    }

    public SupplierDTO getSupplier(String id) {
        GetResponse response = clientService.getClient().prepareGet(INDEX, SUPPLIER_TYPE, id).get();
        byte[] source = response.getSourceAsBytes();
        return source != null ? mapper.jsonToSupplierDTO(source) : null;
    }

    /*
    curl -XGET 'localhost:9200/dido/supplier/_search?pretty' -d'
    {
        "query" : {
        "bool" :{
            "should":[
            {"match":{"tags":"hello"}},
            {"match":{"tags":"world"}}
            ],
            "minimum_should_match":"1<80%"
        }
    }
    }';*/
    @Override
    public List<Long> getSupplierIdsByTags(List<String> tags) {
        List<Long> supplierIds = new ArrayList<>();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        tags.stream().forEach(t -> boolQueryBuilder.should(QueryBuilders.matchQuery(TAGS_FIELD, t)));

        SearchResponse response = clientService.getClient().prepareSearch(INDEX)
                .setTypes(SUPPLIER_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder.minimumShouldMatch("1<80%"))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
        SearchHit[] hits = response.getHits().getHits();
        Arrays.stream(hits).forEach(h -> supplierIds.add(Long.valueOf(h.getSource().get(ID_FIELD).toString())));
        return supplierIds;
    }

}
