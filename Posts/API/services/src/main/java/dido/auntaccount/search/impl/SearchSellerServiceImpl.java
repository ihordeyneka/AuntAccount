package dido.auntaccount.search.impl;

import dido.auntaccount.dto.SellerDTO;
import dido.auntaccount.mappers.JsonMapper;
import dido.auntaccount.search.SearchSellerService;
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

public class SearchSellerServiceImpl implements SearchSellerService {

    private static final Logger logger = LogManager.getLogger(SearchSellerServiceImpl.class);
    public static final String SELLER_TYPE = "seller";
    public static final String INDEX = "dido";
    public static final String TAGS_FIELD = "tags";
    public static final String ID_FIELD = "id";

    @Inject
    SearchClientService clientService;

    @Inject
    JsonMapper mapper;

    public SearchSellerServiceImpl() {

    }

    public String saveSeller(SellerDTO seller) {
        IndexResponse response = clientService.getClient().prepareIndex(INDEX, SELLER_TYPE, seller.getId().toString())
                .setSource(mapper.sellerDTOToJson(seller)).get();
        return response.getId();
    }

    public SellerDTO getSeller(String id) {
        GetResponse response = clientService.getClient().prepareGet(INDEX, SELLER_TYPE, id).get();
        byte[] source = response.getSourceAsBytes();
        return source != null ? mapper.jsonToSellerDTO(source) : null;
    }

    /*
    curl -XGET 'localhost:9200/dido/seller/_search?pretty' -d'
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
    public List<Long> getSellerIdsByTags(List<String> tags) {
        List<Long> sellerIds = new ArrayList<>();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        tags.stream().forEach(t -> boolQueryBuilder.should(QueryBuilders.matchQuery(TAGS_FIELD, t)));

        SearchResponse response = clientService.getClient().prepareSearch(INDEX)
                .setTypes(SELLER_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder.minimumShouldMatch("1<80%"))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
        SearchHit[] hits = response.getHits().getHits();
        Arrays.stream(hits).forEach(h -> sellerIds.add(Long.valueOf(h.getSource().get(ID_FIELD).toString())));
        return sellerIds;
    }

}
