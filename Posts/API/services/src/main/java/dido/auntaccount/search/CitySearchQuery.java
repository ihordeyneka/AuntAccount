package dido.auntaccount.search;

import dido.auntaccount.dto.LocationDTO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class CitySearchQuery extends LocationSearchQuery {

    public CitySearchQuery(LocationDTO location) {
        super(location);
    }

    @Override
    public void filter(BoolQueryBuilder queryBuilder) {
        QueryBuilder countryQuery = QueryBuilders.matchQuery("location.country.country", location.getCountry().getCountry());
        QueryBuilder cityQuery = QueryBuilders.matchQuery("location.city", location.getCity());
        queryBuilder.must(countryQuery).must(cityQuery);
    }
}
