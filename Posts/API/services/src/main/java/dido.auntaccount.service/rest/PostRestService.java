package dido.auntaccount.service.rest;

import dido.auntaccount.dao.SupplierDAO;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Supplier;
import dido.auntaccount.entities.Tag;
import dido.auntaccount.search.SearchSupplierService;
import dido.auntaccount.service.business.PostService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/posts")
public class PostRestService {

    @Inject
    private SupplierDAO supplierDAO;

    @Inject
    PostService postService;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPost(@PathParam("param") Long postId) {
        Post post = postService.getPost(postId);
        return Response.status(200).entity(post).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response savePost(Post post) throws Exception {
        Post savedPost = postService.savePost(post);
        return Response.status(200).entity(savedPost).build();
    }

    @GET
    @Path("/{param}/offers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostOffers(@PathParam("param") Long postId) {
        List<Offer> offers = postService.getPostOffers(postId);
        return Response.status(200).entity(offers).build();
    }
}