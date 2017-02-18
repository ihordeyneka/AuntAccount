package dido.auntaccount.service.rest;


import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.ReviewDTO;
import dido.auntaccount.dto.UserDTO;
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
        UserDTO user = userService.getUser(userId);
        return getResponseBuilder().status(200).entity(user).build();
    }

    @GET
    @Path("/email/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam("param") String email) {
        UserDTO user = userService.findByEmail(email);
        return getResponseBuilder().status(200).entity(user).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(UserDTO user) throws Exception {
        String hashedPassword = passwordService.createHash(user.getPassword());
        user.setPassword(hashedPassword);
        UserDTO savedUser = userService.saveUser(user);
        return getResponseBuilder().status(200).entity(savedUser).build();
    }


    /// TODO: remove later
    @OPTIONS
    @Path("/")
    public Response saveUserPreflight(UserDTO user) {
        return getResponseBuilder().build();
    }

    @GET
    @Path("/{param}/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPosts(@PathParam("param") Long userId) {
        List<PostDTO> posts = userService.getUserPosts(userId);
        return getResponseBuilder().status(200).entity(posts).build();
    }

    @GET
    @Path("/{param}/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserReviews(@PathParam("param") Long userId) {
        List<ReviewDTO> reviews = userService.getUserReviews(userId);
        return getResponseBuilder().status(200).entity(reviews).build();
    }

    private Response.ResponseBuilder getResponseBuilder() {
        return Response.ok().header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, DELETE, OPTIONS, POST")
                .header("Access-Control-Allow-Headers", "Content-Type, x-http-method-override");
    }


}
