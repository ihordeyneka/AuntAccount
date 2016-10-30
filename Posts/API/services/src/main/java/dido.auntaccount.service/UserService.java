package dido.auntaccount.service;

import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserService {

    @Inject
    private UserDAO userDAO;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("param") Long userId) {
        User user = userDAO.find(userId);
        return Response.status(200).entity(user).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(User user) throws Exception {
        User savedUser = userDAO.save(user);
        return Response.status(200).entity(savedUser).build();
    }

    @GET
    @Path("/{param}/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPosts(@PathParam("param") Long userId) {
        List<Post> posts = userDAO.getPostsByUserId(userId);
        return Response.status(200).entity(posts).build();
    }

    @GET
    @Path("/{param}/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserReviews(@PathParam("param") Long userId) {
        List<Review> reviews = userDAO.getReviewsByUserId(userId);
        return Response.status(200).entity(reviews).build();
    }

}
