package dido.auntaccount.service.rest.controller;


import dido.auntaccount.dto.*;
import dido.auntaccount.service.business.PasswordService;
import dido.auntaccount.service.business.TokenService;
import dido.auntaccount.service.business.UserService;
import dido.auntaccount.service.filter.Secured;
import org.apache.commons.io.IOUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.naming.AuthenticationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static dido.auntaccount.service.filter.AuthenticationFilter.LOGGED_IN_USER;

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
        final UserProfileDTO existingUser = userService.findByEmail(email);
        if (existingUser != null) {
            throw new AuthenticationException("Can't create user. Email already exists " + email);
        }
        UserProfileDTO user = new UserProfileDTO(email, firstName, lastName, password);
        String hashedPassword = passwordService.createHash(password);
        user.setPassword(hashedPassword);
        UserDTO savedUser = userService.saveUser(user);
        return getResponseBuilder().entity(savedUser).build();
    }

    @GET
    @Path("/activate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateUser(@QueryParam("token") String verificationToken) throws Exception {
        final VerificationTokenDTO token = userService.getVerificationToken(verificationToken);
        Date now = DateTime.now().toDate();
        if (token == null || token.getExpirationDate().before(now)) {
            throw OAuthProblemException.error("Token is not valid");
        }
        final UserDTO user = token.getUser();
        userService.activateUser(user);
        return getResponseBuilder().build();
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

    @GET
    @Path("/{param}/notifications")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserNotifications(@PathParam("param") Long userId, @QueryParam("offset") Integer offset,
                                         @QueryParam("limit") Integer limit, @HeaderParam(LOGGED_IN_USER) String loggedInUserId) {
        if (!userId.equals(Long.valueOf(loggedInUserId))) {
            throw new NotAuthorizedException("Could't retrieve notification for not logged in user " + userId);
        }
        final NotificationListDTO notifications = userService.getUserNotifications(userId, offset, limit);
        return getResponseBuilder().entity(notifications).build();
    }

    @POST
    @Secured
    @Path("/profile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(UserProfileDTO user, @HeaderParam(LOGGED_IN_USER) String loggedInUserId) throws Exception {
        user.setId(Long.valueOf(loggedInUserId));
        userService.updateUser(user);
        return getResponseBuilder().entity("{}").build();
    }

    @POST
    @Secured
    @Path("/picture")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePicture(@FormDataParam("file_data") InputStream uploadedInputStream,
                                  @HeaderParam(LOGGED_IN_USER) String loggedInUserId) throws Exception {
        userService.updatePicture(Long.valueOf(loggedInUserId), IOUtils.toByteArray(uploadedInputStream));
        return getResponseBuilder().build();
    }

    @POST
    @Secured
    @Path("/password")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(PasswordDTO passwordDTO, @HeaderParam(LOGGED_IN_USER) String loggedInUserId) throws Exception {
        final UserProfileDTO user = userService.getUserProfile(Long.valueOf(loggedInUserId));
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

    @OPTIONS
    @Path("/{param}/notifications")
    public Response getUserNotificationsPreflight() {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/activate")
    public Response activateUserPreflight(@PathParam("token") String verificationToken) throws Exception {
        return getResponseBuilder().build();
    }
}
