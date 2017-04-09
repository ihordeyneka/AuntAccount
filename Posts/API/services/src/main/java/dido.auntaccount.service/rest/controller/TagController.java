package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.ReviewDTO;
import dido.auntaccount.service.filter.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/tag")
public class TagController extends Controller {

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryTags(@PathParam("param") String tag) {
        List<String> tags = Arrays.asList(tag, "1", "2", "3");
        return getResponseBuilder().entity(tags).build();
    }

    @OPTIONS
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryTagsPreflight(@PathParam("param") String tag) {
        return getResponseBuilder().build();
    }

}
