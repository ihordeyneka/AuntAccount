package dido.auntaccount.search;

import dido.auntaccount.dto.LocationDTO;
import org.elasticsearch.index.query.BoolQueryBuilder;

import static dido.auntaccount.search.LocationSearch.*;

public class LocationSearchQuery {

    protected LocationDTO location;

    protected LocationSearchQuery(LocationDTO location) {
        this.location = location;
    }

    public static LocationSearchQuery getSearchQuery(LocationDTO location) {
        final LocationSearch locationSearch = classifySearch(location);
        return locationSearch.getSearchQuery(location);
    }

    public void filter(BoolQueryBuilder queryBuilder) {

    }

    public static LocationSearch classifySearch(LocationDTO locationDTO) {
        if (locationDTO.isGlobal()) {
            return LocationSearch.GLOBAL;
        } else if(locationDTO.getRadius() > 0) {
            return LocationSearch.PLACE;
        } else if (locationDTO.getCity() != null) {
            return LocationSearch.CITY;
        } else if (locationDTO.getCountry() != null) {
            return LocationSearch.COUNTRY;
        }
        throw new IllegalStateException("Can't identify location search type");
    }
}
