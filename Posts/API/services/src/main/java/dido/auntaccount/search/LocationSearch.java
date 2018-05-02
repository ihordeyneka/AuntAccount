package dido.auntaccount.search;

import dido.auntaccount.dto.LocationDTO;

public enum LocationSearch {

    GLOBAL {
        @Override
        public LocationSearchQuery getSearchQuery(LocationDTO locationDTO) {
            return new LocationSearchQuery(locationDTO);
        }
    },
    CITY {
        @Override
        public LocationSearchQuery getSearchQuery(LocationDTO locationDTO) {
            return new CitySearchQuery(locationDTO);
        }
    },
    COUNTRY {
        @Override
        public LocationSearchQuery getSearchQuery(LocationDTO locationDTO) {
            return new CountrySearchQuery(locationDTO);
        }
    },
    PLACE {
        @Override
        public LocationSearchQuery getSearchQuery(LocationDTO locationDTO) {
            return new RadiusSearchQuery(locationDTO);
        }
    };

    public abstract LocationSearchQuery getSearchQuery(LocationDTO locationDTO);


}