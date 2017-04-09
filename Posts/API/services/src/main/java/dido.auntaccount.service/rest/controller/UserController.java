package dido.auntaccount.service.rest.controller;


import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.ReviewDTO;
import dido.auntaccount.dto.UserDTO;
import dido.auntaccount.service.business.PasswordService;
import dido.auntaccount.service.business.UserService;
import dido.auntaccount.service.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserController extends Controller {

    @Inject
    private UserService userService;

    @Inject
    PasswordService passwordService;

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("param") Long userId) {
        UserDTO user = userService.getUser(userId);
        return getResponseBuilder().status(200).entity(user).build();
    }

    @GET
    @Path("/email/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam("param") String email) {
        UserDTO user = userService.findByEmail(email);
        return getResponseBuilder().status(200).entity(user).build();
    }

    @POST
    @Path("/")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(@FormParam("email") String email, @FormParam("firstName") String firstName,
                             @FormParam("lastName") String lastName, @FormParam("password") String password) throws Exception {
        UserDTO user = new UserDTO(email, firstName, lastName, password);
        String hashedPassword = passwordService.createHash(password);
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
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPosts(@PathParam("param") Long userId) {
        List<PostDTO> posts = userService.getUserPosts(userId);
        return getResponseBuilder().status(200).entity(posts).build();
    }

    @OPTIONS
    @Path("/{param}/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPostsPreflight(@PathParam("param") Long userId) {
        return getResponseBuilder().build();
    }


    @GET
    @Path("/{param}/reviews")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserReviews(@PathParam("param") Long userId) {
        List<ReviewDTO> reviews = userService.getUserReviews(userId);
        return getResponseBuilder().status(200).entity(reviews).build();
    }

}
