package dido.auntaccount.service.business.impl;

import dido.auntaccount.search.client.SearchClientService;
import dido.auntaccount.service.business.TagService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.suggest.Suggest;
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

    curl -XPUT 'localhost:9200/dido?pretty' -H 'Content-Type: application/json' -d'
{
    "settings" : {
        "number_of_shards" : 1
    },
    "mappings" : {
        "seller" : {
            "properties" : {
                "tagList" : { "type" : "completion" }
            }
        }
    }
}
'

  curl -XPUT 'localhost:9200/dido?pretty' -H 'Content-Type: application/json' -d'
{
    "settings" : {
         "analysis" : {
        "analyzer" : {
            "my_analyzer" : {
                "tokenizer" : "standard",
                "filter" : ["standard",  "my_word_delimiter"]
            }
        },
        "filter" : {
            "my_word_delimiter" : {
                "type" : "word_delimiter",
                "preserve_original": "true"
            }
        }
    }
    },

    "mappings" : {
        "seller" : {
            "properties" : {
                "tagList" : { "type" : "completion" },
                 "location": {
                    "properties": {
                        "point": {
                            "type": "geo_point"
                        }
                    }
                }
            }
        }
    }

}
'


curl -XPUT 'localhost:9200/dido?pretty' -H 'Content-Type: application/json' -d'
{
  "dido" : {
    "analysis" : {
        "analyzer" : {
            "my_analyzer" : {
                "tokenizer" : "standard",
                "filter" : ["standard", "lowercase", "my_word_delimiter"]
            }
        },
        "filter" : {
            "my_word_delimiter" : {
                "type" : "word_delimiter",
                "preserve_original": "true"
            }
        }
    }
  }
}

    */

    @Inject
    SearchClientService clientService;

    public static final String INDEX = "dido";
    public static final String TAGS_FIELD = "tagList";
    public static final String SELLER_TYPE = "seller";

    @Override
    public List<String> queryTags(String tag) {
        SearchResponse searchResponse = clientService.getClient().prepareSearch(INDEX).suggest(new SuggestBuilder()
                .addSuggestion(SELLER_TYPE,  SuggestBuilders.completionSuggestion(TAGS_FIELD).text(tag).skipDuplicates(true))).get();

        List<String> results = new ArrayList<>();
        Suggest suggest = searchResponse.getSuggest();
        if (suggest != null) {
            suggest.forEach(suggestion -> suggestion.forEach(entry -> entry.forEach(option -> results.add(option.getText().string()))));
        }
        return results;
    }

}
