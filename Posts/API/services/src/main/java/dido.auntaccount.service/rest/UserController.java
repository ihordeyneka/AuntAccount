package dido.auntaccount.service.rest;


import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.entities.User;
import dido.auntaccount.service.business.PasswordService;
import dido.auntaccount.service.business.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserController {

    @Inject
    private UserService userService;

    @Inject
    PasswordService passwordService;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("param") Long userId) {
        User user = userService.getUser(userId);
        return Response.status(200).entity(user).build();
    }

    @GET
    @Path("/name/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByName(@PathParam("param") String name) {
        User user = userService.findByUserName(name);
        return Response.status(200).entity(user).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(User user) throws Exception {
        String hashedPassword = passwordService.createHash(user.getPassword());
        user.setPassword(hashedPassword);
        User savedUser = userService.saveUser(user);
        return Response.status(200).entity(savedUser).build();
    }

    @GET
    @Path("/{param}/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPosts(@PathParam("param") Long userId) {
        List<Post> posts = userService.getUserPosts(userId);
        return Response.status(200).entity(posts).build();
    }

    @GET
    @Path("/{param}/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserReviews(@PathParam("param") Long userId) {
        List<Review> reviews = userService.getUserReviews(userId);
        return Response.status(200).entity(reviews).build();
    }


}
