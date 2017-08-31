package dido.auntaccount.search.impl;

import dido.auntaccount.dto.LocationDTO;
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
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
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
    public static final String TAGS_FIELD = "tagList";
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
    curl -XGET 'localhost:9200/_all/_mapping?pretty'
    curl -XPUT 'localhost:9200/dido/_mapping/seller' -d '
    {
          "properties" : {
            "tags" : {
              "type" : "completion"
        }
      }
    }';


     curl -XPUT 'localhost:9200/dido/_mapping/seller' -d '
    {
         "properties": {
                "location": {
                    "properties": {
                        "point": {
                            "type": "geo_point"
                        }
                    }
                }
            }
    }';

    curl -XGET 'localhost:9200/dido/seller/_search?pretty' -d'
    {
        "query" : {
        "bool" :{
            "should":[
            {"match":{"tagsList":"hello"}},
            {"match":{"tagsList":"world"}}
            ],
            "minimum_should_match":"1<80%"
        }
    }
    }';

    curl -XGET 'localhost:9200/dido/seller/_search?pretty' -d'
    {
        "query" : {
        "bool" :{
            "should":[
            {"match":{"tagsList":"phone"}},
            {"match":{"tagsList":"cell"}}
            ],
            "minimum_should_match":"1<80%",
             "filter" : {
                "geo_distance" : {
                    "distance" : "200km",
                    "location.point" : {
                        "lat" : 50,
                        "lon" : 25
                    }
                }
            }
        }
    }
    }';

      curl -XGET 'localhost:9200/dido/seller/_search?pretty' -d'
    {
        "query" : {
        "bool" :{
            "should":[
            {"match":{"tagsList":"phone"}},
             {"match":{"tagsList":"lvlv"}}
            ],
             "filter" : {
                "geo_distance" : {
                    "distance" : "200km",
                    "location.point" : {
                        "lat" : 49,
                        "lon" : 25
                    }
                }
            }
        }
    }
    }';

      curl -XGET 'localhost:9200/dido/seller/_search?pretty' -d'
    {
        "query" : {
        "bool" :{
        "minimum_should_match":3,
            "should":[
            {"match":{"tagList":"phone"}},
             {"match":{"tagList":"cell"}},
             {"match":{"tagList":"tel"}}
            ]
        }
    }
    }';

     curl -XGET 'localhost:9200/dido/seller/_search?pretty' -d'
    {
        "query" : {
        "bool" :{
            "should":[
            {"match":{"tagList":"phone"}},
            {"match":{"tagList":"cell"}}
            ],
            "minimum_should_match":"1<80%",
             "filter" : {
                "geo_distance" : {
                    "distance" : "5000m",
                    "location.point" : {
                        "lat" : 49.8133236,
                        "lon" :  23.987989800000037
                    }
                }
            }
        }
    }
    }';
    */

    @Override
    public List<Long> getSellerIdsByTagsAndLocation(List<String> tags, LocationDTO location) {
        List<Long> sellerIds = new ArrayList<>();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        tags.stream().forEach(t -> boolQueryBuilder.should(QueryBuilders.matchQuery(TAGS_FIELD, t)));

        QueryBuilder distanceQuery = QueryBuilders.geoDistanceQuery("location.point")
                .point(location.getPoint().getLat(), location.getPoint().getLon())
                .distance(location.getRadius(), DistanceUnit.METERS);

        SearchResponse response = clientService.getClient().prepareSearch(INDEX)
                .setTypes(SELLER_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder.minimumShouldMatch("1<80%").filter(distanceQuery))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
        SearchHit[] hits = response.getHits().getHits();
        Arrays.stream(hits).forEach(h -> sellerIds.add(Long.valueOf(h.getSource().get(ID_FIELD).toString())));
        return sellerIds;
    }

}
