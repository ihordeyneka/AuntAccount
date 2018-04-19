package dido.auntaccount.service.rest.controller;

import dido.auntaccount.service.business.TagService;
import dido.auntaccount.service.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tag")
public class TagController extends Controller {

    @Inject
    TagService tagService;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryTags(@PathParam("param") String tag) {
        List<String> tags = tagService.queryTags(tag);
        return getResponseBuilder().entity(tags).build();
    }

    @OPTIONS
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryTagsPreflight(@PathParam("param") String tag) {
        return getResponseBuilder().build();
    }

}
