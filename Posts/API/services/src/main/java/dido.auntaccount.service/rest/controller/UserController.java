package dido.auntaccount.service.rest.controller;


import dido.auntaccount.dto.*;
import dido.auntaccount.service.business.PasswordService;
import dido.auntaccount.service.business.TokenService;
import dido.auntaccount.service.business.UserService;
import dido.auntaccount.service.filter.Secured;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

@Path("/users")
public class UserController extends Controller {

    @Inject
    private UserService userService;

    @Inject
    PasswordService passwordService;

    @Inject
    TokenService tokenService;

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("param") Long userId) {
        UserDTO user = userService.getUser(userId);
        return getResponseBuilder().entity(user).build();
    }

    @GET
    @Path("/profile/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserProfile(@PathParam("param") Long userId) {
        UserProfileDTO user = userService.getUserProfile(userId);
        return getResponseBuilder().entity(user).build();
    }

    @POST
    @Path("/")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(@FormParam("email") String email, @FormParam("firstName") String firstName,
                             @FormParam("lastName") String lastName, @FormParam("password") String password) throws Exception {
        UserProfileDTO user = new UserProfileDTO(email, firstName, lastName, password);
        String hashedPassword = passwordService.createHash(password);
        user.setPassword(hashedPassword);
        UserDTO savedUser = userService.saveUser(user);
        return getResponseBuilder().entity(savedUser).build();
    }

    @GET
    @Path("/{param}/posts")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPosts(@PathParam("param") Long userId) {
        List<PostDTO> posts = userService.getUserPosts(userId);
        return getResponseBuilder().entity(posts).build();
    }

    @GET
    @Path("/{param}/sellers")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserSellers(@PathParam("param") Long userId) {
        List<SellerDTO> sellers = userService.getUserSellers(userId);
        return getResponseBuilder().entity(sellers).build();
    }

    @POST
    @Secured
    @Path("/profile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(UserProfileDTO user, @HeaderParam(TokenController.ACCESS_TOKEN) String token) throws Exception {
        TokenDTO foundToken = tokenService.getToken(token);
        user.setId(foundToken.getUserId());
        userService.updateUser(user);
        return getResponseBuilder().entity("{}").build();
    }

    @GET
    @Path("/{param}/reviews")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserReviews(@PathParam("param") Long userId) {
        List<ReviewDTO> reviews = userService.getUserReviews(userId);
        return getResponseBuilder().entity(reviews).build();
    }

    @POST
    @Secured
    @Path("/picture")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePicture(@FormDataParam("file_data") InputStream uploadedInputStream, @HeaderParam(TokenController.ACCESS_TOKEN) String token) throws Exception {
        TokenDTO foundToken = tokenService.getToken(token);
        userService.updatePicture(foundToken.getUserId(), IOUtils.toByteArray(uploadedInputStream));
        return getResponseBuilder().build();
    }

    @POST
    @Secured
    @Path("/password")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(PasswordDTO passwordDTO, @HeaderParam(TokenController.ACCESS_TOKEN) String token) throws Exception {
        TokenDTO foundToken = tokenService.getToken(token);
        final UserProfileDTO user = userService.getUserProfile(foundToken.getUserId());
        boolean passwordCorrect = passwordService.verifyPassword(passwordDTO.getOldPassword(), user.getPassword());
        if (passwordCorrect) {
            user.setPassword(passwordService.createHash(passwordDTO.getNewPassword()));
            userService.updateUser(user);
        }
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/{param}/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserReviewsPreflight(@PathParam("param") Long userId) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPreflight(@PathParam("param") Long userId) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/email/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmailPreflight(@PathParam("param") String email) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/")
    public Response saveUserPreflight(UserDTO user) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/{param}/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPostsPreflight(@PathParam("param") Long userId) {
        return getResponseBuilder().build();
    }


    @OPTIONS
    @Path("/profile")
    public Response updateProfilePreflight() throws Exception {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/picture")
    public Response updatePicturePreflight() throws Exception {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/password")
    public Response changePasswordPreflight() throws Exception {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/{param}/sellers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserSellersPreflight(@PathParam("param") Long userId) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/profile/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserProfilePreflight(@PathParam("param") Long userId) {
        return getResponseBuilder().build();
    }
}
