package dido.auntaccount.service.rest.controller;

import dido.auntaccount.service.filter.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/location")
public class LocationController extends Controller {

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryLocations(@PathParam("param") String location) {
        List<String> locations = Arrays.asList(location, "1", "2", "3");
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
