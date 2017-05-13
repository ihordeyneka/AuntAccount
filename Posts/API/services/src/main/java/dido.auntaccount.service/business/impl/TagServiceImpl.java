package dido.auntaccount.service.business.impl;

import dido.auntaccount.search.client.SearchClientService;
import dido.auntaccount.service.business.TagService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class TagServiceImpl implements TagService {

   /* curl -XPOST 'localhost:9200/dido/_search?pretty&pretty' -H 'Content-Type: application/json' -d'
    {
        "suggest": {
        "seller" : {
            "prefix" : "amer",
                    "completion" : {
                "field" : "tags"
            }
        }
    }
    }'

    curl -XPUT localhost:9200/dido/_mapping/seller -d '
    {
      "properties" : {
        "tags" : {
          "type" : "completion"
      }
     }
    }'
    */

    @Inject
    SearchClientService clientService;

    public static final String INDEX = "dido";
    public static final String TAGS_FIELD = "tags";
    public static final String SELLER_TYPE = "seller";

    @Override
    public List<String> queryTags(String tag) {
        SearchResponse searchResponse = clientService.getClient().prepareSearch(INDEX).suggest(new SuggestBuilder()
                .addSuggestion(SELLER_TYPE, SuggestBuilders.completionSuggestion(TAGS_FIELD).text(tag))).get();

        List<String> results = new ArrayList<>();
        searchResponse.getSuggest().forEach(suggestion -> suggestion.forEach(entry -> entry.forEach(option -> results.add(option.getText().string()))));
        return results;
    }

}
