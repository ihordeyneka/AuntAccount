package dido.auntaccount.service;

import dido.auntaccount.dao.PostDAO;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Post;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/posts")
public class PostService {

    @Inject
    private PostDAO postDAO;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPost(@PathParam("param") Long postId) {
        Post post = postDAO.find(postId);
        return Response.status(200).entity(post).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response savePost(Post post) throws Exception {
        Post savedPost = postDAO.save(post);
        return Response.status(200).entity(savedPost).build();
    }

    @GET
    @Path("/{param}/offers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostOffers(@PathParam("param") Long postId) {
        List<Offer> offers = postDAO.getOffersByPostId(postId);
        return Response.status(200).entity(offers).build();
    }
}