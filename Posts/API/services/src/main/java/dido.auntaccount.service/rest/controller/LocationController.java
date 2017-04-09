package dido.auntaccount.service.rest.controller;

import dido.auntaccount.service.business.LocationService;
import dido.auntaccount.service.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/location")
public class LocationController extends Controller {

    @Inject
    LocationService locationService;

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryLocations(@PathParam("param") String location) {
        List<String> locations = locationService.queryLocations(location);
        return getResponseBuilder().entity(locations).build();
    }

    @OPTIONS
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryLocationsPreflight(@PathParam("param") String location) {
        return getResponseBuilder().build();
    }

}
