package dido.auntaccount.search;

import dido.auntaccount.dto.LocationDTO;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class RadiusSearchQuery extends LocationSearchQuery {

    public RadiusSearchQuery(LocationDTO location) {
        super(location);
    }

    @Override
    public void filter(BoolQueryBuilder queryBuilder) {
        QueryBuilder distanceQuery = QueryBuilders.geoDistanceQuery("location.point")
                .point(location.getPoint().getLat(), location.getPoint().getLon())
                .distance(location.getRadius(), DistanceUnit.METERS);
        queryBuilder.filter(distanceQuery);
    }
}
