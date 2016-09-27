package dido.auntaccount.service;

import dido.auntaccount.dao.PostDAO;
import dido.auntaccount.entities.Post;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public String getPost(@PathParam("param") Long id) {
        return postDAO.find(id).getDescription();
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response savePost(String description) throws Exception {
        Post post = new Post().setDescription(description);
        postDAO.save(post);
        return Response.status(200).entity(post).build();
    }
}