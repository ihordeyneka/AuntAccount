package dido.auntaccount.service.business.impl;

import dido.auntaccount.service.business.LocationService;

import java.util.Arrays;
import java.util.List;

public class LocationServiceImpl implements LocationService {

    @Override
    public List<String> queryLocations(String location) {
        return Arrays.asList(location);
    }
}
