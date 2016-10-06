package dido.auntaccount.service;

import dido.auntaccount.dao.PostDAO;
import dido.auntaccount.entities.Post;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by orysiadeyneka on 19.03.16.
 */
@Path("/post")
public class PostService {

    @Inject
    PostDAO postDAO;

    @GET
    @Path("/get/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPost(@PathParam("param") Long id) {
        Post post = postDAO.find(id);
        return Response.status(200).entity(post).build();
    }

    @GET
    @Path("/user/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPosts(@PathParam("param") Long userId) {
        List<Post> post = postDAO.findByUser(userId);
        return Response.status(200).entity(post).build();
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response savePost(Post post) throws Exception {
        postDAO.save(post);
        return Response.status(200).entity(post).build();
    }
}