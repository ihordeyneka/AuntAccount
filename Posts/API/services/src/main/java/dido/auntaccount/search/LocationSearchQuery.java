package dido.auntaccount.search;

import dido.auntaccount.dto.LocationDTO;
import org.elasticsearch.index.query.BoolQueryBuilder;

public class LocationSearchQuery {

    protected LocationDTO location;

    protected LocationSearchQuery(LocationDTO location) {
        this.location = location;
    }

    public static LocationSearchQuery getSearchQuery(LocationDTO location) {
        if (location.getRadius() != 0) {
            return new RadiusSearchQuery(location);
        } else if (location.getCity() != null) {
            return new CitySearchQuery(location);
        } else if (location.getCountry() != null) {
            return new CountrySearchQuery(location);
        }
        return new LocationSearchQuery(location);

    }

    public void filter(BoolQueryBuilder queryBuilder) {

    }
}
